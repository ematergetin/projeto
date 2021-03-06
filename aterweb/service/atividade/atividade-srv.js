/* global removerCampo */

(function(pNmModulo, pNmFactory, pNmController) {

'use strict';

angular.module(pNmModulo).factory(pNmFactory,
  ['$rootScope', '$http', 'toastr', 'SegurancaSrv', 'UtilSrv', '$stateParams', 'ComunidadeSrv',
    function($rootScope, $http, toastr, SegurancaSrv, UtilSrv, $stateParams, ComunidadeSrv) {
        'ngInject';
        
        var AtividadeSrv = {
            funcionalidade: 'ATIVIDADE',
            endereco: $rootScope.servicoUrl + '/atividade',
            enderecoProjetoCredito: $rootScope.servicoUrl + '/projeto-credito-rural',
            abrir : function(scp) {
              
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
                UtilSrv.dominio({ent: [
                   'PessoaGenero',
                   'PessoaGeracao',
                   'PessoaSituacao',
                   'PublicoAlvoSegmento',
                   'PublicoAlvoCategoria',
                   'AtividadeFormato',
                   'AtividadeFinalidade',
                   'AtividadeNatureza',
                   'AtividadePrioridade',
                   'AtividadeSituacao',
                   'Metodo',
                   'Assunto',
                   'CadeiaProdutiva',
                   'Confirmacao',
                   'MetaTatica'
                ]}).success(function(resposta) {
                    if (resposta && resposta.resultado) {
                        removerCampo(resposta.resultado, ['@jsonId']);
                        scp.cadastro.apoio.generoList = resposta.resultado[0];
                        scp.cadastro.apoio.pessoaGeracaoList = resposta.resultado[1];
                        scp.cadastro.apoio.pessoaSituacaoList = resposta.resultado[2];
                        scp.cadastro.apoio.publicoAlvoSegmentoList = resposta.resultado[3];
                        scp.cadastro.apoio.publicoAlvoCategoriaList = resposta.resultado[4];
                        scp.cadastro.apoio.atividadeFormatoList = resposta.resultado[5];
                        scp.cadastro.apoio.atividadeFinalidadeList = resposta.resultado[6];
                        scp.cadastro.apoio.atividadeNaturezaList = resposta.resultado[7];
                        scp.cadastro.apoio.atividadePrioridadeList = resposta.resultado[8];
                        scp.cadastro.apoio.atividadeSituacaoList = resposta.resultado[9];
                        scp.cadastro.apoio.metodoList = resposta.resultado[10];
                        scp.cadastro.apoio.assuntoList = resposta.resultado[11];
                        scp.cadastro.apoio.cadeiaProdutivaList = resposta.resultado[12];
                        scp.cadastro.apoio.confirmacaoList = resposta.resultado[13];
                        scp.cadastro.apoio.metaTaticaList = [];
                        var anoAtual = new Date().getFullYear();
                        anoAtual += '';
                        resposta.resultado[14].forEach(function(item) {
                          if( item.ano === anoAtual ){
                            scp.cadastro.apoio.metaTaticaList.push(item);
                          } 
                        });
                   }
                });
                if ($rootScope.isAuthenticated()) {
                    var t = $rootScope.token;

                    scp.cadastro.apoio.localList = [];
                    var fltr = null;
                    if (scp.cadastro.apoio.unidadeOrganizacionalSomenteLeitura) {
                        fltr = {unidadeOrganizacionalList: scp.cadastro.filtro.unidadeOrganizacionalList};
                    } else {
                        fltr = {pessoaJuridicaList: (t && t.lotacaoAtual && t.lotacaoAtual.pessoaJuridica) ? [angular.fromJson(t.lotacaoAtual.pessoaJuridica.id)] : null};
                    }
                    ComunidadeSrv.lista(fltr, scp.cadastro.apoio.localList, t);

                    if (!t || !t.lotacaoAtual || !t.lotacaoAtual.pessoaJuridica) {
                        toastr.warning('Não foi possível identificar a sua lotação', 'Erro ao carregar os dados');
                    }
                }

                //scp.cadastro.filtro.inicio = new Date();
                //scp.cadastro.filtro.inicio.setMonth(scp.cadastro.filtro.inicio.getMonth() - 6);
                //scp.cadastro.filtro.inicio.setDate(1);
                //scp.cadastro.filtro.termino = new Date();

            },
            filtrar : function(filtro) {
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
                if( $stateParams.opcao !== "projetoCredito" ){
                    return $http.post(this.endereco + '/filtro-executar', filtro);
                } else {
                    return $http.post(this.enderecoProjetoCredito + '/filtro-executar', filtro );
                }
            },

            projetoTecnicoRel: function(idList) {
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
                if (idList && !angular.isArray(idList)) {
                    var temp = idList;
                    idList = [];
                    idList.push(temp);
                }
                return $http.get(this.enderecoProjetoCredito + '/projeto-tecnico-rel', {params: {'idList': idList}});
            }, 


            filtroNovo: function() {
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
                return $http.get(this.endereco + '/filtro-novo', {params: {'opcao': $stateParams.opcao}});
            },
            executarFiltro : function() {
                SegurancaSrv.acesso(this.funcionalidade, 'CONSULTAR');
            },
            novo : function() {
                SegurancaSrv.acesso(this.funcionalidade, 'INCLUIR');
                return $http.get(this.endereco + '/novo', {params: {'opcao': $stateParams.opcao}});
            },
            incluir : function(atividade) {
                SegurancaSrv.acesso(this.funcionalidade, 'INCLUIR');
                return $http.post(this.endereco + '/incluir', atividade);
            },
            incluirAtividade : function(atividade) {
                SegurancaSrv.acesso(this.funcionalidade, 'INCLUIR');
                return $http.post(this.endereco + '/incluirAtividade', atividade);
               //console.log(atividade);
            },
            visualizar : function(id) {
                SegurancaSrv.acesso(this.funcionalidade, 'VISUALIZAR');
                return $http.get(this.endereco + '/visualizar', {params: {'id': id}});
            },
            editar : function(atividade) {
                SegurancaSrv.acesso(this.funcionalidade, 'EDITAR');
                return $http.post(this.endereco + '/editar', atividade);
            },
            salvar: function(registro) {
                //removerCampo(registro, ['@jsonId']);
                if (registro.id) {
                    return this.editar(registro);
                } else {
                    return this.incluir(registro);
                }
            },

            excluir : function(registro) {
                SegurancaSrv.acesso(this.funcionalidade, 'EXCLUIR');
                return $http.delete(this.endereco + '/excluir', {params: {'id': registro.id}});
            },
            tagUnidade : function(nome) {
                return $http.post($rootScope.servicoUrl + '/unidade-organizacional/lista', {"nome":nome, "classificacao":["OP"]}, { cache: false } );
            },
            tagComunidade : function( unidade, nome) {
                return ComunidadeSrv.lista({"unidadeOrganizacionalList": [unidade], "nome":nome});
            },

        };
        return AtividadeSrv;
    }
]);

})('principal', 'AtividadeSrv');


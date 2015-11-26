/* global criarEstadosPadrao */

(function(pNmModulo, pNmController, pNmFormulario, pUrlModulo) {
    'use strict';
    angular.module(pNmModulo, ['ui.bootstrap', 'ui.utils', 'ui.router', 'ngAnimate', 'frz.navegador', 'frz.form', 'ngSanitize']);
    angular.module(pNmModulo).config(['$stateProvider', function($stateProvider) {
        criarEstadosPadrao($stateProvider, pNmModulo, pNmController, pUrlModulo);
    }]);
    angular.module(pNmModulo).controller(pNmController, ['$scope', 'toastr', 'FrzNavegadorParams', '$state', '$rootScope', '$modal', '$log', '$modalInstance', 'modalCadastro', 'UtilSrv', 'mensagemSrv', 'FormularioSrv',
        function($scope, toastr, FrzNavegadorParams, $state, $rootScope, $modal, $log, $modalInstance, modalCadastro, UtilSrv, mensagemSrv, FormularioSrv) {

            // inicializacao
            $scope.crudInit($scope, $state, null, pNmFormulario, FormularioSrv);

            // código para verificar se o modal está ou não ativo
            $scope.verificaEstado($modalInstance, $scope, 'filtro', modalCadastro, pNmFormulario);
            // inicio: atividades do Modal
            $scope.modalOk = function() {
                // Retorno da modal
                $modalInstance.close($scope.navegador.selecao);
            };
            $scope.modalCancelar = function() {
                // Cancelar a modal
                $modalInstance.dismiss('cancel');
                toastr.warning('Operação cancelada!', 'Atenção!');
            };
            $scope.modalAbrir = function(size) {
                // abrir a modal
                var template = '<ng-include src=\"\'' + pNmModulo + '/' + pNmModulo + '-modal.html\'\"></ng-include>';
                var modalInstance = $modal.open({
                    animation: true,
                    template: template,
                    controller: pNmController,
                    size: size,
                    resolve: {
                        modalCadastro: function() {
                            return {filtro: {}, lista: [], registro: {}, original: {}, apoio: [],};
                        }
                    }
                });
                // processar retorno da modal
                modalInstance.result.then(function(cadastroModificado) {
                    // processar o retorno positivo da modal
                    $scope.navegador.setDados(cadastroModificado.lista);
                }, function() {
                    // processar o retorno negativo da modal
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };

            // fim: atividades do Modal

            // inicio das operaçoes atribuidas ao navagador

            // fim das operaçoes atribuidas ao navagador

            // inicio ações especiais
            $scope.cadastro.apoio.elementoTipoList = [];
            
            $scope.cadastro.apoio.formulario = {
                codigo: 'formulario',
                tipo: 'array',
                nome: 'Formulário',
                opcao:
                    [
                        {
                            nome: 'Nome do Formulário',
                            codigo: 'nome',
                            tipo: 'string',
                            funcaoRequirido: function() {return true;},
                        },
                        {
                            nome: 'Código do Formulário',
                            codigo: 'codigo',
                            tipo: 'string',
                            funcaoRequirido: function() {return true;},
                            tamanho: 3,
                        },
                        {
                            nome: 'Situação',
                            codigo: 'situacao',
                            tipo: 'escolha_unica',
                            funcaoRequirido: function() {return true;},
                            opcao: {
                                codigo: 'codigo',
                                descricao: 'descricao',
                                lista: [{codigo: 'A', descricao: 'Ativo'},{codigo: 'I', descricao: 'Inativo'},],
                            }
                        },
                        {
                            nome: 'Início da Coleta',
                            codigo: 'inicio',
                            tipo: 'data',
                            funcaoRequirido: function() {return true;},
                            tamanho: 2,
                        },
                        {
                            nome: 'Término da Coleta',
                            codigo: 'termino',
                            tipo: 'data',
                            tamanho: 2,
                        },
                        {
                            codigo: 'formularioVersaoList',
                            tipo: 'array',
                            nome: 'Versões do Formulário',
                            funcaoRequirido: function() {return true;},
                            opcao:
                                [
                                    {
                                        nome: 'Versão',
                                        codigo: 'versao',
                                        tipo: 'numero',
                                        tamanho: 2,
                                        somenteLeitura: true,
                                        opcao: {
                                            fracao: '0',
                                        }
                                    },
                                    {
                                        nome: 'Data de Início de Validade',
                                        codigo: 'data',
                                        tipo: 'data',
                                        funcaoRequirido: function() {return false;},
                                        tamanho: 2,
                                    },
                                    {
                                        codigo: 'formularioVersaoElementoList',
                                        tipo: 'array',
                                        nome: 'Elementos do Formulário',
                                        opcao:
                                            [
                                                {
                                                    nome: 'Elemento',
                                                    codigo: 'elemento',
                                                    tipo: 'objeto',
                                                    funcaoRequirido: function() {return true;},
                                                    funcaoExibir: function(dados) {
                                                        if (!dados) {
                                                            return null;
                                                        }
                                                        var str = 
                                                            '<table class="table table-hover table-striped">' + 
                                                            '    <thead>'+
                                                            '       <tr>' +
                                                            '           <th>Nome</th>' +
                                                            '           <th>Código</th>' +
                                                            '           <th>Tipo</th>' +
                                                            '       </tr>' +
                                                            '    </thead>' +
                                                            '    <tbody>' +
                                                            '       <tr>' +
                                                            '           <td>' + dados.nome + '</td>' +
                                                            '           <td>' + dados.codigo + '</td>' +
                                                            '           <td>' + dados.tipo.nome + '</td>' +
                                                            '       </tr>' +
                                                            '    <tbody>' +
                                                            '</table>';
                                                        return str;
                                                    },
                                                    opcao: [
                                                        {
                                                            nome: 'Nome',
                                                            codigo: 'nome',
                                                            tipo: 'string',
                                                            funcaoRequirido: function() {return true;},
                                                        },
                                                        {
                                                            nome: 'Código',
                                                            codigo: 'codigo',
                                                            tipo: 'string',
                                                            funcaoRequirido: function() {return true;},
                                                        },
                                                        {
                                                            nome: 'Tipo',
                                                            codigo: 'tipo',
                                                            tipo: 'escolha_unica',
                                                            funcaoRequirido: function() {return true;},
                                                            opcao: {
                                                                codigo: 'codigo',
                                                                descricao: 'descricao',
                                                                lista: $scope.cadastro.apoio.elementoTipoList,
                                                            },

                                                        },
                                                        {
                                                            nome: 'Esconder no Formulário',
                                                            codigo: 'escondeForm',
                                                            tipo: 'escolha_unica',
                                                            opcao: {
                                                                codigo: 'id',
                                                                descricao: 'nome',
                                                                lista: [{id: 'S', nome: 'Sim'}, {id: 'N', nome: 'Não'}],
                                                            },
                                                        },
                                                        {
                                                            nome: 'Esconder na Lista',
                                                            codigo: 'escondeLista',
                                                            tipo: 'escolha_unica',
                                                            opcao: {
                                                                codigo: 'id',
                                                                descricao: 'nome',
                                                                lista: [{id: 'S', nome: 'Sim'}, {id: 'N', nome: 'Não'}],
                                                            },
                                                        },
                                                        {
                                                            nome: 'Função Requerido',
                                                            codigo: 'requerido',
                                                            tipo: 'memo',
                                                        },
                                                        {
                                                            nome: 'Função Incluir Antes',
                                                            codigo: 'funcaoIncluirAntes',
                                                            tipo: 'memo',
                                                        },
                                                        {
                                                            nome: 'Função Editar Antes',
                                                            codigo: 'funcaoEditarAntes',
                                                            tipo: 'memo',
                                                        },
                                                        {
                                                            nome: 'Função Excluir Antes',
                                                            codigo: 'funcaoExcluirAntes',
                                                            tipo: 'memo',
                                                        },
                                                        {
                                                            nome: 'Função Excluir Depois',
                                                            codigo: 'funcaoExcluirDepois',
                                                            tipo: 'memo',
                                                        },
                                                        {
                                                            nome: 'Função Salvar Depois',
                                                            codigo: 'funcaoSalvarDepois',
                                                            tipo: 'memo',
                                                        },
                                                        {
                                                            nome: 'Opções',
                                                            codigo: 'opcaoList',
                                                            tipo: 'array',
                                                            opcao: {},
                                                        },
                                                    ],
                                                },
                                                {
                                                    nome: 'Ordem',
                                                    codigo: 'ordem',
                                                    tipo: 'numero',
                                                    funcaoRequirido: function() {return true;},
                                                    tamanho: 2,
                                                    opcao: {
                                                        fracao: '0',
                                                    }
                                                },
                                            ],
                                    },
                                ],
                        },
                    ],
            };
            // fim ações especiais

            // inicio trabalho tab
            // fim trabalho tab

            // inicio dos watches
            $scope.$watch('cadastro.apoio.formulario.opcao[5].opcao[2].opcao[0].formAtual.tipo', function() {
                if (!$scope.cadastro.apoio.formulario.opcao[5].opcao[2].opcao[0].formAtual) {
                    return;
                }
                var tipo = $scope.cadastro.apoio.formulario.opcao[5].opcao[2].opcao[0].formAtual.tipo;
                for (var i in $scope.cadastro.apoio.elementoTipoList) {
                    if ($scope.cadastro.apoio.elementoTipoList[i].codigo === tipo) {
                        $scope.cadastro.apoio.formulario.opcao[5].opcao[2].opcao[0].opcao[11].opcao = $scope.cadastro.apoio.elementoTipoList[i].opcao;
                        $scope.cadastro.apoio.formulario.opcao[5].opcao[2].opcao[0].formAtual.opcaoList = null;

                        $scope.cadastro.apoio.formulario.opcao[5].opcao[2].opcao[0].opcao[11].escondeForm = $scope.cadastro.apoio.elementoTipoList[i].opcao ? 'N': 'S';
                        break;
                    }
                }
            }, true);
            // fim dos watches
        }
    ]);
})('formulario', 'FormularioCtrl', 'Cadastro de Formularios', 'formulario');
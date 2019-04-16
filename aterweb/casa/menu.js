(function(pNmModulo, pNmController, pNmFormulario) {

'use strict';

  angular.module(pNmModulo).controller(pNmController, ['$scope', '$rootScope', 'SegurancaSrv', '$state', 
    function ($scope, $rootScope, SegurancaSrv, $state) {
    'ngInject';
    
    $scope.init = function () {
        SegurancaSrv.usuarioLogado()
        .success(function(user) {
            if (user && user.username && user.username !== 'anonymousUser') {
                // For display purposes only
                if ($rootScope.isAuthenticated(user.username)) {
                    return;
                }
            }
            if (!$state.is('info')) {
                $scope.executarLogout();
            }
        }).error(function(a, b, c, d, e) {
            console.log(a, b, c, d, e);
        });
    };

    // este menu sera carregado pelo login do usuario
    var initTree = function() {
        $scope.tree = [
            {
                name: 'Info',
                visivel: true,
                subtree: [
                    {
                        name: 'Webmail',
                        link: 'info({"nome": "Webmail", "endereco": "https://cas.gdfnet.df.gov.br","xw_width":"100%" , "xw_height":"750"})',
                        visivel: true,
                    },
                    {
                        name: 'Extranet',
                        link: 'info({"nome": "Extranet", "endereco": "http://extranet.emater.df.gov.br","xw_width":"100%" , "xw_height":"750"})',
                        visivel: true,
                    },
                    {
                        name: 'Internet',
                        link: 'info({"nome": "Internet", "endereco": "http://www.emater.df.gov.br","xw_width":"100%" , "xw_height":"750"})',
                        visivel: true,
                    },
                    /*
                    {
                        name: 'Planejamento',
                        link: 'info({"nome": "Planejamento", "endereco": "http://extranet.emater.df.gov.br/planejamento"})',
                        visivel: true,
                    },
                    {
                        name: 'Painel de Resultados',
                        link: 'info({"nome": "Painel de Resultados", "endereco": "https://painel.emater.df.gov.br"})',
                        visivel: true,
                    },
                    */

                ],
            },
            {
                name: 'Cadastro',
                visivel: false,
                subtree: [
                    {
                        name: 'Pessoa',
                        link: 'p.pessoa.filtro',
                        funcionalidade: 'PESSOA',
                        visivel: false,
                    },
                    {
                        name: 'Propriedade Rural',
                        link: 'p.propriedadeRural.filtro',
                        funcionalidade: 'PROPRIEDADE_RURAL',
                        visivel: false,
                    },
                    {
                        name: 'Diagonósticos',
                        link: 'info({"nome": "teste", "endereco": "ematerweb", "programa":"coleta", "xw_width":"100%" , "xw_height":"900" })',
                        visivel: true,
                    },
                    {
                        name: 'Oganizações Sociais',
                        link: 'info({"nome": "teste", "endereco": "ematerweb", "programa":"associacao", "xw_width":"100%" , "xw_height":"900" })',
                        visivel: true,
                    },
/*                    {
                        name: 'Formulários',
                        link: 'p.formulario.filtro',
                        funcionalidade: 'FORMULARIO',
                        visivel: false,
                    },  
*/                    {
                        name: 'Grupo Social',
                        link: 'p.grupoSocial.filtro',
                        funcionalidade: 'GRUPO_SOCIAL',
                        visivel: false,
                    },
                    {
                        name: 'Contratos & Convênios',
                        link: 'p.contrato.filtro',
                        funcionalidade: 'CONTRATO',
                        visivel: false,
                    } ,
                ]
            },
            {
                name: 'ATER',
                link: '#',
                visivel: false,
                subtree: [

                   {
                        name: 'Registro de Atividade - Novo ',
                        link: 'info({"nome": "teste", "endereco": "ematerweb","programa":"atividade", "xw_width":"100%" , "xw_height":"900" })',
                        visivel: true,
                    },
 
                    {
                        name: 'Planejamento - Novo ',
                        link: 'info({"nome": "teste", "endereco": "ematerweb","programa":"planejamento", "xw_width":"100%" , "xw_height":"900" })',
                        visivel: true,
                    },
 
 
/*                    {  
                        name: 'Registro de Atividade - 2019 ',
                        link: 'p.atividade.filtro({opcao: "executar"})',
                        funcionalidade: 'ATIVIDADE',
                        visivel: false,
                    },
                     
                    {   
                        name: 'Projeto de Crédito Rural',
                        link: 'p.atividade.filtro({opcao: "projetoCredito"})',
                        funcionalidade: 'PROJETO_CREDITO_RURAL',
                        visivel: false,
                    },
*/                    
                    {
                        name: 'Digitar Índice de Produção  (desabilitado)',
                        link: 'xxXSSS',
                        //link: 'p.indiceProducao.form',
                        //link: 'p.indiceProducao.filtro({opcao: "projetoCredito"})',
                        funcionalidade: 'INDICE_PRODUCAO',
                        visivel: false,
                    },
                    
                    {
                        name: 'Visualizar Índice de Produção',
                        link: 'info({"nome": "teste", "endereco": "ematerweb","programa":"ipa", "xw_width":"100%" , "xw_height":"900" })',
                        visivel: true,
                    },

             ]
            },

            {
                name: 'Vídeo Ajuda',
                link: 'login',
                visivel: false,
                subtree: [

                   {
                        name: 'IPA - Novo  ',
                        link: 'info({"nome": "teste", "endereco": "video","programa":"ipa", "xw_width":"950" , "xw_height":"650" })',
                        visivel: true,
                    },

                   {
                        name: 'Organizações Sociais  ',
                        link: 'info({"nome": "teste", "endereco": "video","programa":"associacoes", "xw_width":"950" , "xw_height":"650" })',
                        visivel: true,
                    },

                   {
                        name: 'Planejamento  ',
                        link: 'info({"nome": "teste", "endereco": "video","programa":"planejamento", "xw_width":"950" , "xw_height":"650" })',
                        visivel: true,
                    },
             ]
            },
            {
                name: 'Configuração',
                link: 'login',
                visivel: false,
                subtree: [
                    {
                        name: 'Funcionalidade',
                        link: 'p.funcionalidade.filtro',
                        funcionalidade: 'FUNCIONALIDADE',
                        visivel: false,
                    },
                    {
                        name: 'Perfil',
                        link: 'p.perfil.filtro',
                        funcionalidade: 'PERFIL',
                        visivel: false,
                    },
                    {
                        name: 'Usuário',
                        link: 'p.usuario.filtro',
                        funcionalidade: 'USUARIO',
                        visivel: false,
                    },
                    {
                        name: 'Log',
                        link: 'p.logAcao.filtro',
                        funcionalidade: 'LOG_ACAO',
                        visivel: false,
                    },
                ],
            },
        ];
    };

    var ativar = function(item, arvore, raiz) {
        var retorno = false;
        for (var ramo in arvore) {
            if (arvore[ramo].funcionalidade === item) {
                arvore[ramo].visivel = true;
                if (raiz) {
                    for (var r in raiz) {
                        raiz[r].visivel = true;
                    }
                }
                retorno = true;
            } 
            if (arvore[ramo].subtree) {
                if (!angular.isArray(raiz)) {
                    raiz = [];
                }
                raiz.push(arvore[ramo]);
                if (ativar(item, arvore[ramo].subtree, raiz)) {
                    retorno = true;
                } else {
                    raiz = [];
                }
            }
        }
        return retorno;
    };

    var removerInvisiveis = function (arvore) {
        if (!arvore) {
            return;
        }
        var tot = arvore.length -1;
        for (var ramo = tot; ramo >= 0; ramo--) {            
            if (!arvore[ramo].visivel) {
                arvore.splice(ramo, 1);
            } else {
                if (arvore[ramo].subtree) {
                    removerInvisiveis(arvore[ramo].subtree);
                }
            }
        }
    };

    $scope.$watch('token', function (newValue) {
        if (!$scope.tree) {
            initTree();
        }
        if (!newValue) {
            initTree();
        } else {
            for (var fc in newValue.funcionalidadeComandoList) {
                ativar(fc, $scope.tree);
            }
            removerInvisiveis($scope.tree);
        }
    });

    $scope.cadastro = {apoio: {moduloList: []}};
    $scope.cadastro.apoio.moduloList = [{
        codigo: 1,
        nome: 'Principal'
    }, {
        codigo: 2,
        nome: 'Compras'
    }, {
        codigo: 3,

        nome: 'Crédito'
    }, {
        codigo: 4,
        nome: 'Funcional'
    }, {
        codigo: 5,
        nome: 'Institucinal'
    }, {
        codigo: 6,
        nome: 'Orçamento'
    }, {
        codigo: 7,
        nome: 'Patrimônio'
    }, ];
    $scope.moduloAcesso = 1;

  }]);

})('principal', 'MenuCtrl', 'Menu da Tela Principal');
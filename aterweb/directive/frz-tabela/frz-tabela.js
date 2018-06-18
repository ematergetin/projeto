/* jslint evil: true, browser: true, plusplus: true, loopfunc: true */

/*
    codigo para executar funcoes
        var f = null;
        eval ("f = function(nome) {console.log(nome);}");
        f('kiko');
*/

(function() {
    'use strict';

    var FrzTabelaModule = angular.module('frz.tabela', ['frz.navegador', 'principal']);

    var verificaDados = function(scope) {
        if (!angular.isArray(scope.dados)) {
            scope.dados = [];
        }
        scope.navegadorFrm.setDados(scope.dados);
        scope.selecao = scope.navegadorFrm.selecao;
    };

    // controller para a barra de navegacao
    FrzTabelaModule.controller('FrzTabelaCtrl', ['$scope', 'FrzNavegadorParams', 'mensagemSrv', 'UtilSrv', function($scope, FrzNavegadorParams, mensagemSrv, UtilSrv) {
        'ngInject';

        $scope.navegadorFrm = new FrzNavegadorParams([]);
        var abrirForm = function (form, dd, acao) {
            verificaDados($scope);
            if (!dd) {
                dd = {};
            }
            var dados = angular.copy(dd);
            var conteudo = {formulario: form, dados: dados};
            var conf = '<frz-form ng-model="conteudo.formulario" dados="conteudo.dados">';

            if (acao === 'visualizar'){
                mensagemSrv.vizualiza(false, conf, form.nome, conteudo).then(function(conteudo){});
                
            } else {

                mensagemSrv.confirmacao(false, conf, form.nome, conteudo).then(function(conteudo) {
                    // processar o retorno positivo da modal
                    if (acao === 'incluir') {
                        $scope.dados.push(conteudo.dados);
                    } else if (acao === 'editar') {
                        for (var i in $scope.dados) {
                            if (angular.equals($scope.dados[i], dd)) {
                                angular.copy(conteudo.dados, $scope.dados[i]);
                                angular.copy(conteudo.dados, dd);
                                break;
                            }
                        }
                    }
                    if ($scope.ngModel.funcaoSalvarDepois) {
                        $scope.ngModel.funcaoSalvarDepois(form, dd, acao);
                    }
                }, function() {
                    // processar o retorno negativo da modal
                    //$log.info('Modal dismissed at: ' + new Date());
                });
            }
        };
        $scope.incluir = function (form) {
            try {
                var dd = {}, ret ={};
                var validaIncluirAntes = true;
                if ($scope.ngModel.funcaoIncluirAntes) {
                    ret = $scope.ngModel.funcaoIncluirAntes(form, dd);
                    validaIncluirAntes = ret.valida ? ret.valida : false;
                    dd = ret.reg ? ret.reg : {};
                }
                if( (validaIncluirAntes!==false) ){
                    abrirForm(form, dd, 'incluir');
                }
                $scope.selecao = $scope.navegadorFrm.selecao;
            } catch (exception) {
            }
        };
        $scope.editar = function (form) {
            try {
                var validaEditarAntes = true;
                if ($scope.navegadorFrm.selecao.tipo === 'U') {
                    if ($scope.ngModel.funcaoEditarAntes) {
                        validaEditarAntes = $scope.ngModel.funcaoEditarAntes(form, $scope.navegadorFrm.selecao.item);
                    }
                    if( (validaEditarAntes!==false) ){
                        abrirForm(form, $scope.navegadorFrm.selecao.item, 'editar');
                        if ($scope.ngModel.funcaoEditarDepois) {
                            $scope.ngModel.funcaoEditarDepois(form, $scope.navegadorFrm.selecao.item);
                        }
                    }
                    if( (validaEditarAntes===false) ){
                        abrirForm(form, $scope.navegadorFrm.selecao.item, 'visualizar');
                    }
                } else {
                    for (var i in $scope.navegadorFrm.selecao.items) {
                        validaEditarAntes = true;
                        if ($scope.ngModel.funcaoEditarAntes) {
                            validaEditarAntes = $scope.ngModel.funcaoEditarAntes(form, $scope.navegadorFrm.selecao.items[i]);
                        }
                        if( (validaEditarAntes!==false) ){
                            abrirForm(form, $scope.navegadorFrm.selecao.items[i], 'editar');
                            if ($scope.ngModel.funcaoEditarDepois) {
                                $scope.ngModel.funcaoEditarDepois(form, $scope.navegadorFrm.selecao.items[i]);
                            }
                        }
                    }
                }
                $scope.selecao = $scope.navegadorFrm.selecao;
            } catch (exception) {
            }
        };
        var remove = function (reg) {
            var pos = UtilSrv.indiceDe($scope.navegadorFrm.dados, reg);
            $scope.navegadorFrm.dados.splice(pos, 1);
        };
        $scope.excluir = function (form) {
            try {
                var validaExcluirAntes = true;
                if ($scope.navegadorFrm.selecao.tipo === 'U') {
                    if ($scope.ngModel.funcaoExcluirAntes) {
                       validaExcluirAntes =  $scope.ngModel.funcaoExcluirAntes(form, $scope.navegadorFrm.selecao.item);
                    }
                    if( (validaExcluirAntes!==false) ){
                        remove($scope.navegadorFrm.selecao.item);
                        if ($scope.ngModel.funcaoExcluirDepois) {
                            $scope.ngModel.funcaoExcluirDepois(form, $scope.navegadorFrm.selecao.item, validaExcluirAntes);
                        }
                    }
                    $scope.navegadorFrm.selecao.item = null;
                } else {
                    for (var i in $scope.navegadorFrm.selecao.items) {
                        if ($scope.ngModel.funcaoExcluirAntes) {
                            validaExcluirAntes = $scope.ngModel.funcaoExcluirAntes(form, $scope.navegadorFrm.selecao.items[i]);
                        }
                        if( (validaExcluirAntes!==false) ){
                            remove($scope.navegadorFrm.selecao.items[i]);
                            if ($scope.ngModel.funcaoExcluirDepois) {
                                $scope.ngModel.funcaoExcluirDepois(form, $scope.navegadorFrm.selecao.items[i], validaExcluirAntes);
                            }
                        }
                    }
                    $scope.navegadorFrm.selecao.items.length = 0;
                }
                $scope.selecao = $scope.navegadorFrm.selecao;
            } catch (exception) {
            }
        };
        $scope.verMultiplo = function(valor, opcao, objeto) {
            var result = '';
            for (var i in valor) {
                if (i > 0) {
                    result += ', ';
                }
                if (objeto) {
                    result += $scope.verUnicoObjeto(valor[i], opcao, objeto);
                } else {
                    result += $scope.verUnico(valor[i], opcao, objeto);
                }
            }
            if (result) {
                result = '[' + result + ']';
            }
            return result;
        };
        $scope.verUnico = function(valor, opcao, objeto) {
            if (!valor || (objeto && !valor[opcao.opcao.codigo])) {
                return;
            }
            for (var i in opcao.opcao.lista) {
                if (objeto && opcao.opcao.lista[i][opcao.opcao.codigo] === valor[opcao.opcao.codigo]) {
                    return opcao.opcao.lista[i][opcao.opcao.descricao];
                } else if (opcao.opcao.lista[i][opcao.opcao.codigo] === valor) {
                    return opcao.opcao.lista[i][opcao.opcao.descricao];
                }
            }
        };
        $scope.verMultiploObjeto = function(valor, opcao) {
            return $scope.verMultiplo(valor, opcao, true);
        };
        $scope.verUnicoObjeto = function(valor, opcao, objeto) {
            if (valor && valor[opcao.opcao.descricao]) {
                return valor[opcao.opcao.descricao];
            }
            return null;
        };
        $scope.verArray = function(form, dd) {

            verificaDados($scope);
            var acao = null;
            var dados = null;
            if (!dd) {
                dados = {};
                acao = 'incluir';
            } else {
                dados = angular.copy(dd);
                acao = 'editar';
            }
            var conteudo = {formulario: form, dados: dados};
            var conf = '<frz-tabela ng-model="conteudo.formulario" dados="conteudo.dados">';
            mensagemSrv.confirmacao(false, conf, form.nome, conteudo).then(function(conteudo) {
                // processar o retorno positivo da modal
                angular.copy(conteudo.dados, dd);
            }, function() {
                // processar o retorno negativo da modal
                //$log.info('Modal dismissed at: ' + new Date());
            });
        };
        $scope.verObjeto = function(form, dd) {


            if ($scope.ngModel.funcaoExibirAntes) {
                $scope.ngModel.funcaoExibirAntes(form, dd);
            }   

            verificaDados($scope);
            var acao = null;
            var dados = null;
            if (!dd) {
                dados = {};
                acao = 'incluir';
            } else {
                dados = angular.copy(dd);
                acao = 'editar';
            }
            var conteudo = {formulario: form, dados: dados};
            var conf = '<frz-form ng-model="conteudo.formulario" dados="conteudo.dados">';
            mensagemSrv.confirmacao(false, conf, form.nome, conteudo).then(function(conteudo) {
                // processar o retorno positivo da modal
                angular.copy(conteudo.dados, dd);
            }, function() {
                // processar o retorno negativo da modal
                //$log.info('Modal dismissed at: ' + new Date());
            });
        };
    }]);

    // diretiva da barra de navegação de dados
    FrzTabelaModule.directive('frzTabela', ['FrzNavegadorParams', function(FrzNavegadorParams) {
        'ngInject';
        
        return {
            require: ['^ngModel'],
            restrict: 'E', 
            replace: true,
            templateUrl: 'directive/frz-tabela/frz-tabela.html',
            scope: {
                ngModel: '=',
                dados: '=',
                selecao: '=?',
                funcaoRequerido: '=?',
                onAbrir: '&',
            },
            controller: 'FrzTabelaCtrl',
            link: function(scope, element, attributes, $parse) {
                verificaDados(scope);

                // registrar os observadores
                if (scope.ngModel.observar) {
                    for (var i in scope.ngModel.observar) {
                        scope.$watch(scope.ngModel.observar[i].expressao, scope.ngModel.observar[i].funcao, scope.ngModel.observar[i].colecao);
                    }
                }

                scope.navegadorFrm.mudarEstado('ESPECIAL');

                // executar o estado inicial da tabela
                if (scope.ngModel.onAbrir) {
                    scope.ngModel.onAbrir();
                }

            },
        };
    }]);

}());
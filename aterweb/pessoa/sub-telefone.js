(function(pNmModulo, pNmController, pNmFormulario) {

'use strict';

angular.module(pNmModulo).controller(pNmController,
    ['$scope', 'FrzNavegadorParams', '$modal', '$modalInstance', 'toastr', 'UtilSrv', 'mensagemSrv',
    function($scope, FrzNavegadorParams, $modal, $modalInstance, toastr, UtilSrv, mensagemSrv) {

    // inicializacao
    var init = function() {
        if (!angular.isObject($scope.cadastro.registro.telefoneList)) {
            $scope.cadastro.registro.telefoneList = [];
        }
        $scope.pessoaTelefoneNvg = new FrzNavegadorParams($scope.cadastro.registro.telefoneList, 4);
    };
    if (!$modalInstance) { init(); }

    // inicio rotinas de apoio
    // $scope.seleciona = function(pessoaTelefoneNvg, item) { };
    // $scope.mataClick = function(pessoaTelefoneNvg, event, item){ };
    // fim rotinas de apoio

    // inicio das operaçoes atribuidas ao navagador
    $scope.abrir = function() { $scope.pessoaTelefoneNvg.mudarEstado('ESPECIAL'); };

    $scope.agir = function() {};
    $scope.ajudar = function() {};
    $scope.alterarTamanhoPagina = function() {};
    $scope.cancelar = function() {};
    $scope.cancelarEditar = function() {};
    $scope.cancelarExcluir = function() {};
    $scope.cancelarFiltrar = function() {};
    $scope.cancelarIncluir = function() {};
    $scope.confirmar = function() {};
    $scope.confirmarEditar = function() {};
    $scope.confirmarExcluir = function() {};
    $scope.confirmarFiltrar = function() {};
    $scope.confirmarIncluir = function() {};
    $scope.excluir = function() {};
    $scope.filtrar = function() {};
    $scope.folhearAnterior = function() {};
    $scope.folhearPrimeiro = function() {};
    $scope.folhearProximo = function() {};
    $scope.folhearUltimo = function() {};
    $scope.editar = function() {  $scope.incluir(); };
    $scope.incluir = function() {
        var item = {};
        $scope.abreModal(item);
    };
    $scope.informacao = function() {};
    $scope.limpar = function() {};
    $scope.paginarAnterior = function() {};
    $scope.paginarPrimeiro = function() {};
    $scope.paginarProximo = function() {};
    $scope.paginarUltimo = function() {};
    $scope.restaurar = function() {};
    $scope.visualizar = function() {};
    $scope.voltar = function() {};
    // fim das operaçoes atribuidas ao navagador

    $scope.abreModal = function (item) {
        // abrir a modal
        mensagemSrv.confirmacao(true, 'pessoa-telefone-frm.html', item.numero, item, item.tamanho ).then(function (conteudo) {
            // processar o retorno positivo da modal
            var item  = {meioContato: conteudo};
            $scope.cadastro.registro.telefoneList.push(item);
        }, function () {
            // processar o retorno negativo da modal
            //$log.info('Modal dismissed at: ' + new Date());
        });

    };    

} // fim função
]);

})('pessoa', 'PessoaTelefoneCtrl', 'Telefone vinculado à pessoa');
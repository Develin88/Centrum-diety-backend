window.getPatientsAjaxRequest = params => {
    const url = '/api/accounts/findPatients'
    const requestParams = {
        size: params.data.limit,  // Przykład zmiany nazwy parametrów
        page: Math.floor(params.data.offset / params.data.limit),
        textToSearch: params.data.search
    };
    $.get(`${url}?${$.param(requestParams)}`).then(function (res) {
        const transformedResponse = {
            total: res.totalElements,
            totalNotFiltered: res.totalElements,
            rows: res.content
        };

        params.success(transformedResponse);
    })
}

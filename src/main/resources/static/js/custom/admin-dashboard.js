const PatientsTable = (() => {
    const fetchPatientsData = params => {
        const url = '/api/accounts/findPatients';
        const requestParams = {
            size: params.data.limit,
            page: Math.floor(params.data.offset / params.data.limit),
            textToSearch: params.data.search
        };

        $.get(`${url}?${$.param(requestParams)}`)
            .then(res => {
                const transformedResponse = {
                    total: res.totalElements,
                    totalNotFiltered: res.totalElements,
                    rows: res.content
                };
                params.success(transformedResponse);
            })
            .fail(() => {
                params.error?.();
            });
    };

    const fetchDietitiansData = params => {
        const url = '/api/accounts/findDietitians';
        const requestParams = {
            size: params.data.limit,
            page: Math.floor(params.data.offset / params.data.limit),
            textToSearch: params.data.search
        };

        $.get(`${url}?${$.param(requestParams)}`)
            .then(res => {
                const transformedResponse = {
                    total: res.totalElements,
                    totalNotFiltered: res.totalElements,
                    rows: res.content
                };
                params.success(transformedResponse);
            })
            .fail(() => {
                params.error?.();
            });
    };

    const getSelectedPatients = () => {
        const $table = $('#patients-table');
        return $table.bootstrapTable('getSelections').map(row => row.userName);
    };

    const getSelectedDietitians = () => {
        const $table = $('#dietitians-table');
        return $table.bootstrapTable('getSelections').map(row => row.userName);
    };


    const init = () => {
        const $patientsTable = $('#patients-table');
        const $dietitiansTable = $('#dietitians-table');
        const $promotePatientsButton = $('#promote-patients-btn');
        const $demoteDietitiansButton = $('#demote-dietitians-btn');

        $patientsTable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', () => {
            $promotePatientsButton.prop('disabled', !$patientsTable.bootstrapTable('getSelections').length);
        });

        $dietitiansTable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', () => {
            $demoteDietitiansButton.prop('disabled', !$dietitiansTable.bootstrapTable('getSelections').length);
        });

        $promotePatientsButton.click(function () {
            const userNameSelections = getSelectedPatients();
            const csrfToken = $('meta[name="_csrf"]').attr('content');
            const csrfHeader = $('meta[name="_csrf_header"]').attr('content');
            $.ajax({
                type: 'POST',
                url: '/api/accounts/promoteAccountsToDietitians',
                contentType: 'application/json',
                data: JSON.stringify(userNameSelections),
                headers: {
                    [csrfHeader]: csrfToken
                },
                success: function (response) {
                    console.log('✅ Sukces:', response);
                },
                error: function (xhr, status, error) {
                    console.error('❌ Błąd:', error);
                },
                complete: function () {
                    $promotePatientsButton.prop('disabled', true);
                    $patientsTable.bootstrapTable('uncheckAll');
                    $patientsTable.bootstrapTable('refresh');
                    $dietitiansTable.bootstrapTable('refresh');
                }
            });
        });

        $demoteDietitiansButton.click(function () {
            const userNameSelections = getSelectedDietitians();
            const csrfToken = $('meta[name="_csrf"]').attr('content');
            const csrfHeader = $('meta[name="_csrf_header"]').attr('content');
            $.ajax({
                type: 'POST',
                url: '/api/accounts/demoteAccountsToPatients',
                contentType: 'application/json',
                data: JSON.stringify(userNameSelections),
                headers: {
                    [csrfHeader]: csrfToken
                },
                success: function (response) {
                    console.log('✅ Sukces:', response);
                },
                error: function (xhr, status, error) {
                    console.error('❌ Błąd:', error);
                },
                complete: function () {
                    $demoteDietitiansButton.prop('disabled', true);
                    $dietitiansTable.bootstrapTable('uncheckAll');
                    $patientsTable.bootstrapTable('refresh');
                    $dietitiansTable.bootstrapTable('refresh');
                }
            });
        });
    };

    return {
        init,
        fetchPatientsData,
        fetchDietitiansData
    };
})();


PatientsTable.init();
window.fetchPatientsData = PatientsTable.fetchPatientsData;
window.fetchDietitiansData = PatientsTable.fetchDietitiansData;

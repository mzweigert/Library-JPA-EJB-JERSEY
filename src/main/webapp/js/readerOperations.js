$(document).ready(function()
{
    var idReader,
        name,
        surname,
        joinDate,
        extraPoints,
        $updateAlert = $('#update-alert'),
        $inputsUpdate = $('#inputs-update').children(),
        $tbody = $('#reader-tbody');

    $( '.form-date' ).datetimepicker( { format: 'YYYY-MM-DD' } );

    doAjax('../rest/reader/getAllReaders', 'GET', 'JSON').then(
    function(response)
    {
        makeRowsInTable(response, $tbody);
    });

    $tbody.on('click', '.remove-row', function()
    {
        idReader = $(this).closest('tr').children().eq(0).text();
    });

    $("#delete-btn").on('click', function()
    {
        if(typeof idReader != null && typeof idReader != 'undefined' )
        {
            doAjax('../rest/reader/deleteReader', 'DELETE', '', {idReader: idReader}).success(function(response){ location.reload(true); });;
        }
    });

    $tbody.on('click', '.update-row', function()
    {
        var $this = $(this).closest('tr').children();
        $updateAlert.removeClass('in');
        $updateAlert.text('');
        idReader = $this.eq(0).text();
        name = $this.eq(1).text();
        surname = $this.eq(2).text();
        joinDate = $this.eq(3).text();
        extraPoints = $this.eq(4).text();
        $inputsUpdate.eq(0).val(idReader);
        $inputsUpdate.eq(1).val(name);
        $inputsUpdate.eq(2).val(surname);
        $inputsUpdate.eq(3).val(joinDate);
        $inputsUpdate.eq(4).val(extraPoints);
    });

    $('#update-form').submit(function(e)
    {
        $updateAlert.removeClass('in');
        $updateAlert.text('');
        var newIdReader = $inputsUpdate.eq(0).val(),
            newName = $inputsUpdate.eq(1).val(),
            newSurname = $inputsUpdate.eq(2).val(),
            newJoinDate = $inputsUpdate.eq(3).val(),
            newExtraPoints = $inputsUpdate.eq(4).val();
        if(idReader != '' && typeof idReader != 'undefined' && idReader == newIdReader)
        {
            if(name == newName && newSurname == surname && joinDate == newJoinDate && extraPoints == newExtraPoints)
            {
                $('#update-modal').modal('hide');
            }
            else if(newName.length < 2 || newSurname.length < 2)
            {
                $updateAlert.text('Name and surname should have minimum 3 characters!');
                $updateAlert.addClass('in');
            }
            else if(!isValidDate(newJoinDate))
            {
                $updateAlert.text('Join Date is incorrect (correct format is yyyy-mm-dd)');
                $updateAlert.addClass('in');
            }
            else if(isNaN(newExtraPoints) || newExtraPoints == '' || newExtraPoints.length > 10)
            {
                $updateAlert.text('Extra points should be a number (maximum 10 characters)!');
                $updateAlert.addClass('in');
            }
            else
            {
                doAjax('../rest/reader/updateReader', 'PUT', '',
                {
                    idReader: newIdReader,
                    name: newName,
                    surname: newSurname,
                    joinDate: newJoinDate,
                    extraPoints: newExtraPoints

                }).success(function(response){ location.reload(true); });
            }
            e.preventDefault();
        }
    });

    $('#add-btn').click(function()
    {
        var $addAlert = $('#add-alert');
        $addAlert.removeClass('in');
        name = $('#name').val();
        surname = $('#surname').val();
        joinDate = $('#join-date').val();
        extraPoints = $('#extra-points').val();

        if(name.length < 2 || name.length > 30 || surname.length < 2 || surname.length > 30)
        {
        	$addAlert.text('Name and surname should have minimum 3 and maximum 30 characters!');
        	$addAlert.addClass('in');
        }
        else if(!isValidDate(joinDate))
        {
        	$addAlert.text('Join Date is incorrect (correct format is yyyy-mm-dd)');
        	$addAlert.addClass('in');
        }
        else if(isNaN(extraPoints) || extraPoints == '' || extraPoints.length > 10)
        {
        	$addAlert.text('Extra points should be a number (maximum 10 characters)!');
        	$addAlert.addClass('in');
        }
        else
        {
        	 doAjax('../rest/reader/addReader', 'POST', '' , {name: name, surname:surname, joinDate: joinDate, extraPoints: extraPoints})
        	    .success(function(response){ location.reload(true); });
        }
    });

});



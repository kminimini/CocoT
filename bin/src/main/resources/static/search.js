$(document).ready(function() {
    $('#departureSearch').click(function() {
        $.ajax({
            url: '/regions',
            success: function(data) {
                $('#regionSelection').html(data).show();
            }
        });
    });

    $(document).on('click', '#regionSelection li', function() {
        var cityCode = $(this).data('city-code');
        var cityName = $(this).text();
        
        $('#departureStation').val(cityName); // Set the station name in the input field
        $('#regionSelection').hide(); // Hide the region selection
    });
});

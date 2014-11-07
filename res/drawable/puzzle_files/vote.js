function vote(v, g){
    $.get('/div/vote.php?v='+ v + '&g=' + g, function(data) {
        $('#voted').html(data);
    });
}

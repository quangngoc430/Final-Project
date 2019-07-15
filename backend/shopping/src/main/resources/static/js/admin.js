

$(document).ready(function () {

  $("#sidebar").mCustomScrollbar({
       theme: "minimal"
  });

  $('#sidebarCollapse').on('click', function () {
      // open or close navbar
      $('#sidebar, #content').toggleClass('active');
      // close dropdowns
      $('.collapse.in').toggleClass('in');
      // and also adjust aria-expanded attributes we use for the open/closed arrows
      // in our CSS
      $('a[aria-expanded=true]').attr('aria-expanded', 'false');
  });

  $(".custom-file-input").on("change", function() {
    var fileName = $(this).val().split("\\").pop();
    $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
  });

  $("button").click(() => {
    $("#p1").empty();
  });
});
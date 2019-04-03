$(document).ready(function() {
  $(".js--close-addTask").click(function() {
    $("#addTask").removeClass("show");
  });
});

function loadTask(id) {
  $("#single_task")
    .dialog({
      width: 600,
      autoOpen: true,
      dialogClass: "test",
      modal: true,
      responsive: true
    })
    .load("/tasks/" + id, () => {
      $("#single_task").dialog("open");
    });
}

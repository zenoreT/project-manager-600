$(document).ready(function () {
});

function loadTask(id) {
  $("#single_task")
    .dialog({
      autoOpen: false,
      draggable: false,
      modal: true,
      resizable: false,
      width: 750,
      height: 750,
    })
    .load("/tasks/" + id, () => {
      $("#single_task").dialog("open");
    });
}
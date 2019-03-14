$(document).ready(function() {
});

function loadTask(id) {
  $("#task_body_" + id).load("/tasks/" + id);
}
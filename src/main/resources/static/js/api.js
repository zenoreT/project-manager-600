$(document).ready(function () {
  $(".js--close-addTask").click(function () {
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

function postComment() {
  const taskId = $("#comment-task-id").val();
  const content = $("#comment-content").val();

  const formData = new FormData();

  formData.append("content", content);
  formData.append("taskId", taskId);
  fetch("http://localhost:8080/comments/new", {
    method: "post",
    body: formData
  })
    .then(response => response.json())
    .then(data => {
      $("#task-comments").prepend("<div><span>" + data.comment + "</span></div>")
    });
}

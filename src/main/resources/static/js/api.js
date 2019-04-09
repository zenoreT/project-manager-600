$(document).ready(function() {
  $(".js--close-addTask").click(function() {
    $("#addTask").removeClass("show");
  });

  $(".js--single-task").click(function() {
    const dataTaskId = $(this).attr("data-task-id");
    fetch("http://localhost:8080/tasks/" + dataTaskId, {
      method: "get"
    })
      .then(response => response.json())
      .then(data => {
        const task = data.task;
        console.log(task);
        $("#task-title").text(task.name);
        $("#task-startDate").text(task.startDate);
        $("#task-deadlineDate").text(task.deadlineDate);
        if (task.endDate) {
          $(".task-info__deadline-date").append(
            "<div class='task-info__end-date'><span class='font-weight-bold'>Zakończono: </span><span id='task-endDate' class='end-date'></span></div>"
          );
          $("#task-endDate").text(task.endDate);
        }
        $("#task-assignee").text(task.assignee.username);
        $("#task-description").text(task.description);
        if (task.comments && Array.isArray(task.comments)) {
          task.comments.forEach(comment => {
            $("#task-comments").append(
              "<div class='single-comment'><div class='single-comment__info'><span class='single-comment__author'>Autor: " +
                comment.author.username +
                "</span><span class='single-comment__date'> " +
                formatDateTime(comment.creationDate) +
                "</span></div><div class='single-comment__content'>" +
                comment.content +
                "</div></div>"
            );
          });
        }
        $("#task-add-comment").attr("data-task-id", task.id);
      });
  });

  $("#task-add-comment").click(function() {
    const taskId = $(this).attr("data-task-id");
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
        $("#task-comments").prepend(
          "<div class='single-comment'><div class='single-comment__info'><span class='single-comment__author'>Autor: " +
            data.author +
            "</span><span class='single-comment__date'> " +
            test() +
            "</span></div><div class='single-comment__content'>" +
            data.content +
            "</div></div>"
        );
        $("#log-list").prepend(
          "<div class='card' style='white-space: pre-wrap'><div class='card-body'><span>" +
            data.log +
            "</span></div></div>"
        );
      });
  });

  $("#singleTask").on("hidden.bs.modal", function() {
    $("#task-comments").text("");
  });

  function formatDateTime(value) {
    const date = new Date(value);
    return (
      date.toLocaleDateString() +
      " " +
      date.getHours() +
      ":" +
      (date.getMinutes()<10?'0':'') + date.getMinutes() +
      ":" +
      (date.getSeconds()<10?'0':'') + date.getSeconds()
    );
  }

  function test() {
    const date = new Date();
    return (
      date.toLocaleDateString() +
      " " +
      date.getHours() +
      ":" +
      (date.getMinutes()<10?'0':'') + date.getMinutes() +
      ":" +
      (date.getSeconds()<10?'0':'') + date.getSeconds()
    );
  }
});

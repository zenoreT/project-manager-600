const myInput = document.getElementById("password");
const letter = document.getElementById("letter");
const capital = document.getElementById("capital");
const number = document.getElementById("number");
const length = document.getElementById("length");

myInput.onfocus = function () {
  document.getElementById("message").style.display = "block";
};

myInput.onblur = function () {
  document.getElementById("message").style.display = "none";
};

myInput.onkeyup = function () {
  const lowerCaseLetters = /[a-z]/g;
  if (myInput.value.match(lowerCaseLetters)) {
    letter.classList.remove("invalid");
    letter.classList.add("valid");
  } else {
    letter.classList.remove("valid");
    letter.classList.add("invalid");
  }

  const upperCaseLetters = /[A-Z]/g;
  if (myInput.value.match(upperCaseLetters)) {
    capital.classList.remove("invalid");
    capital.classList.add("valid");
  } else {
    capital.classList.remove("valid");
    capital.classList.add("invalid");
  }

  const numbers = /[0-9]/g;
  if (myInput.value.match(numbers)) {
    number.classList.remove("invalid");
    number.classList.add("valid");
  } else {
    number.classList.remove("valid");
    number.classList.add("invalid");
  }
  if (myInput.value.length >= 8) {
    length.classList.remove("invalid");
    length.classList.add("valid");
  } else {
    length.classList.remove("valid");
    length.classList.add("invalid");
  }
};
const password = document.getElementById("password");
const confirm_password = document.getElementById("confirm_password");

function validatePassword() {
  console.log(!password.value.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/g));
  if (password.value !== confirm_password.value) {
    confirm_password.setCustomValidity("Podane hasła nie są takie same");
  } else if (!password.value.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/g)) {
    password.setCustomValidity("Podane hasło nie składa się z wymaganych znaków");
  } else {
    password.setCustomValidity("");
    confirm_password.setCustomValidity("");
  }
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;

const nickname = document.getElementById("nickname");

nickname.onkeyup = function () {
  if (nickname.value.length >= 5) {
    nickname.setCustomValidity("");
  } else {
    nickname.setCustomValidity(
      "Nazwa użytkownika powinna zawierać minimum 5 znaków"
    );
  }
};

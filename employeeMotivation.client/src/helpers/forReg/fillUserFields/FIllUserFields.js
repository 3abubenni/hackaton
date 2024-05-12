"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.FillUserFields = FillUserFields;
var _User = require("../../../entities/User.interface");
function FillUserFields(newUser, value, inputValue) {
  switch (value) {
    case 0:
      newUser.fname = inputValue;
      break;
    case 1:
      newUser.lname = inputValue;
      break;
    case 2:
      newUser.email = inputValue;
      break;
    case 3:
      newUser.password = inputValue;
      break;
    case 4:
      newUser.bday = new Date(inputValue);
      break;
    default:
      break;
  }
  return;
}
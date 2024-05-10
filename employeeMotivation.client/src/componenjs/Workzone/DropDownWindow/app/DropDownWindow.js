"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.DropDownWindow = void 0;
var _reactRouterDom = require("react-router-dom");
require("../styles/DropDownWindowstyles.css");
var DropDownWindow = exports.DropDownWindow = function DropDownWindow(_ref) {
  var showDropWindow = _ref.showDropWindow;
  var navigate = (0, _reactRouterDom.useNavigate)();
  var GoToProfile = function GoToProfile() {
    navigate("profile");
    showDropWindow(false);
  };
  var GoToInventory = function GoToInventory() {
    navigate("inventory");
    showDropWindow(false);
  };
  var GoToUserClan = function GoToUserClan() {
    navigate("userClan");
    showDropWindow(false);
  };
  var GoToUsersTasks = function GoToUsersTasks() {
    navigate("usersTasks");
    showDropWindow(false);
  };
  var GoToUsersNotif = function GoToUsersNotif() {
    navigate("notifications");
    showDropWindow(false);
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("ul", {
    id: "dropDownSelection"
  }, /*#__PURE__*/React.createElement("li", {
    onClick: GoToProfile
  }, "Profile"), /*#__PURE__*/React.createElement("li", {
    onClick: GoToInventory
  }, "Inventory"), /*#__PURE__*/React.createElement("li", {
    onClick: GoToUserClan
  }, "Your clan"), /*#__PURE__*/React.createElement("li", {
    onClick: GoToUsersTasks
  }, "Your tasks"), /*#__PURE__*/React.createElement("li", {
    onClick: GoToUsersNotif
  }, "Notifications")));
};
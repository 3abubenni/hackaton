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
  var handleClickGoToProfile = function handleClickGoToProfile() {
    navigate("profile");
    showDropWindow(false);
  };
  var handleClickGoToInventory = function handleClickGoToInventory() {
    navigate("inventory");
    showDropWindow(false);
  };
  var handleClickGoToUserClan = function handleClickGoToUserClan() {
    navigate("userClan");
    showDropWindow(false);
  };
  var handleClickGoToUsersTasks = function handleClickGoToUsersTasks() {
    navigate("usersTasks");
    showDropWindow(false);
  };
  var handleClickGoToUsersNotif = function handleClickGoToUsersNotif() {
    navigate("notifications");
    showDropWindow(false);
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("ul", {
    id: "dropDownSelection"
  }, /*#__PURE__*/React.createElement("li", {
    onClick: handleClickGoToProfile
  }, "Profile"), /*#__PURE__*/React.createElement("li", {
    onClick: handleClickGoToInventory
  }, "Inventory"), /*#__PURE__*/React.createElement("li", {
    onClick: handleClickGoToUserClan
  }, "Your clan"), /*#__PURE__*/React.createElement("li", {
    onClick: handleClickGoToUsersTasks
  }, "Your tasks"), /*#__PURE__*/React.createElement("li", {
    onClick: handleClickGoToUsersNotif
  }, "Notifications")));
};
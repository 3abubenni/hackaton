"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Workzone = void 0;
var _reactRouterDom = require("react-router-dom");
var _Header = require("../../Header/app/Header");
var _ErrorPage = _interopRequireDefault(require("../../../ErrorPage/ErrorPage"));
var _Tasks = require("../../Tasks/Parent/app/Tasks");
var _Profile = require("../../Profile/app/Profile");
var _Store = require("../../Store/Parent/app/Store");
require("../styles/Workzonestyles.css");
var _ClanList = require("../../ClanList/Parent/app/ClanList");
var _Inventory = require("../../Inventory/Parent/app/Inventory");
var _UsersTasks = require("../../UsersTasks/app/UsersTasks");
var _Notifications = require("../../Notifications/Parent/app/Notifications");
var _UserClan = require("../../UserClan/Parent/app/UserClan");
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
var Workzone = exports.Workzone = function Workzone() {
  var urlNow = (0, _reactRouterDom.useParams)();
  console.log(urlNow);
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement(_Header.Header, null), /*#__PURE__*/React.createElement(_reactRouterDom.Routes, null, /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "tasks",
    element: /*#__PURE__*/React.createElement(_Tasks.Tasks, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "clans",
    element: /*#__PURE__*/React.createElement(_ClanList.ClanList, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "store",
    element: /*#__PURE__*/React.createElement(_Store.Store, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "profile",
    element: /*#__PURE__*/React.createElement(_Profile.Profile, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "inventory",
    element: /*#__PURE__*/React.createElement(_Inventory.Inventory, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "usersTasks",
    element: /*#__PURE__*/React.createElement(_UsersTasks.UsersTasks, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "userClan",
    element: /*#__PURE__*/React.createElement(_UserClan.UserClan, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "notifications",
    element: /*#__PURE__*/React.createElement(_Notifications.Notifications, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "/*",
    element: /*#__PURE__*/React.createElement(_ErrorPage.default, null)
  })));
};
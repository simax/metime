// Compiled by ClojureScript 0.0-2342
goog.provide('dashboard.core');
goog.require('cljs.core');
goog.require('cljs.core.async');
goog.require('cljs_http.client');
goog.require('cljs_http.client');
goog.require('om.dom');
goog.require('om.dom');
goog.require('om.core');
goog.require('om.core');
goog.require('cljs.core.async');
goog.require('goog.events');
goog.require('goog.events');
cljs.core.enable_console_print_BANG_.call(null);
cljs.core.println.call(null,"Hello world!");
dashboard.core.app_state = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
dashboard.core.fetch_departments = (function fetch_departments(url){var c = cljs.core.async.chan.call(null);var c__6424__auto___13197 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6424__auto___13197,c){
return (function (){var f__6425__auto__ = (function (){var switch__6409__auto__ = ((function (c__6424__auto___13197,c){
return (function (state_13188){var state_val_13189 = (state_13188[(1)]);if((state_val_13189 === (2)))
{var inst_13186 = (state_13188[(2)]);var state_13188__$1 = state_13188;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13188__$1,inst_13186);
} else
{if((state_val_13189 === (1)))
{var inst_13175 = cljs.core.PersistentVector.EMPTY_NODE;var inst_13176 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_13177 = ["Department 1"];var inst_13178 = cljs.core.PersistentHashMap.fromArrays(inst_13176,inst_13177);var inst_13179 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_13180 = ["Department 2"];var inst_13181 = cljs.core.PersistentHashMap.fromArrays(inst_13179,inst_13180);var inst_13182 = [inst_13178,inst_13181];var inst_13183 = (new cljs.core.PersistentVector(null,2,(5),inst_13175,inst_13182,null));var inst_13184 = cljs.core.vec.call(null,inst_13183);var state_13188__$1 = state_13188;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13188__$1,(2),c,inst_13184);
} else
{return null;
}
}
});})(c__6424__auto___13197,c))
;return ((function (switch__6409__auto__,c__6424__auto___13197,c){
return (function() {
var state_machine__6410__auto__ = null;
var state_machine__6410__auto____0 = (function (){var statearr_13193 = [null,null,null,null,null,null,null];(statearr_13193[(0)] = state_machine__6410__auto__);
(statearr_13193[(1)] = (1));
return statearr_13193;
});
var state_machine__6410__auto____1 = (function (state_13188){while(true){
var ret_value__6411__auto__ = (function (){try{while(true){
var result__6412__auto__ = switch__6409__auto__.call(null,state_13188);if(cljs.core.keyword_identical_QMARK_.call(null,result__6412__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6412__auto__;
}
break;
}
}catch (e13194){if((e13194 instanceof Object))
{var ex__6413__auto__ = e13194;var statearr_13195_13198 = state_13188;(statearr_13195_13198[(5)] = ex__6413__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13188);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13194;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6411__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13199 = state_13188;
state_13188 = G__13199;
continue;
}
} else
{return ret_value__6411__auto__;
}
break;
}
});
state_machine__6410__auto__ = function(state_13188){
switch(arguments.length){
case 0:
return state_machine__6410__auto____0.call(this);
case 1:
return state_machine__6410__auto____1.call(this,state_13188);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6410__auto____0;
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6410__auto____1;
return state_machine__6410__auto__;
})()
;})(switch__6409__auto__,c__6424__auto___13197,c))
})();var state__6426__auto__ = (function (){var statearr_13196 = f__6425__auto__.call(null);(statearr_13196[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6424__auto___13197);
return statearr_13196;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6426__auto__);
});})(c__6424__auto___13197,c))
);
return c;
});
dashboard.core.department_name = (function department_name(p__13200,owner,opts){var map__13205 = p__13200;var map__13205__$1 = ((cljs.core.seq_QMARK_.call(null,map__13205))?cljs.core.apply.call(null,cljs.core.hash_map,map__13205):map__13205);var department = cljs.core.get.call(null,map__13205__$1,new cljs.core.Keyword(null,"department","department",-359157087));if(typeof dashboard.core.t13206 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13206 = (function (department,map__13205,opts,owner,p__13200,department_name,meta13207){
this.department = department;
this.map__13205 = map__13205;
this.opts = opts;
this.owner = owner;
this.p__13200 = p__13200;
this.department_name = department_name;
this.meta13207 = meta13207;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13206.cljs$lang$type = true;
dashboard.core.t13206.cljs$lang$ctorStr = "dashboard.core/t13206";
dashboard.core.t13206.cljs$lang$ctorPrWriter = ((function (map__13205,map__13205__$1,department){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13206");
});})(map__13205,map__13205__$1,department))
;
dashboard.core.t13206.prototype.om$core$IRender$ = true;
dashboard.core.t13206.prototype.om$core$IRender$render$arity$1 = ((function (map__13205,map__13205__$1,department){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.li(null,React.DOM.span(null,"hello !!!!!"));
});})(map__13205,map__13205__$1,department))
;
dashboard.core.t13206.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__13205,map__13205__$1,department){
return (function (_13208){var self__ = this;
var _13208__$1 = this;return self__.meta13207;
});})(map__13205,map__13205__$1,department))
;
dashboard.core.t13206.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__13205,map__13205__$1,department){
return (function (_13208,meta13207__$1){var self__ = this;
var _13208__$1 = this;return (new dashboard.core.t13206(self__.department,self__.map__13205,self__.opts,self__.owner,self__.p__13200,self__.department_name,meta13207__$1));
});})(map__13205,map__13205__$1,department))
;
dashboard.core.__GT_t13206 = ((function (map__13205,map__13205__$1,department){
return (function __GT_t13206(department__$1,map__13205__$2,opts__$1,owner__$1,p__13200__$1,department_name__$1,meta13207){return (new dashboard.core.t13206(department__$1,map__13205__$2,opts__$1,owner__$1,p__13200__$1,department_name__$1,meta13207));
});})(map__13205,map__13205__$1,department))
;
}
return (new dashboard.core.t13206(department,map__13205__$1,opts,owner,p__13200,department_name,null));
});
dashboard.core.department_list = (function department_list(p__13209){var map__13214 = p__13209;var map__13214__$1 = ((cljs.core.seq_QMARK_.call(null,map__13214))?cljs.core.apply.call(null,cljs.core.hash_map,map__13214):map__13214);var departments = cljs.core.get.call(null,map__13214__$1,new cljs.core.Keyword(null,"departments","departments",799278298));console.log(cljs.core.println.call(null,"list of departments:",departments));
if(typeof dashboard.core.t13215 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13215 = (function (departments,map__13214,p__13209,department_list,meta13216){
this.departments = departments;
this.map__13214 = map__13214;
this.p__13209 = p__13209;
this.department_list = department_list;
this.meta13216 = meta13216;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13215.cljs$lang$type = true;
dashboard.core.t13215.cljs$lang$ctorStr = "dashboard.core/t13215";
dashboard.core.t13215.cljs$lang$ctorPrWriter = ((function (map__13214,map__13214__$1,departments){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13215");
});})(map__13214,map__13214__$1,departments))
;
dashboard.core.t13215.prototype.om$core$IRender$ = true;
dashboard.core.t13215.prototype.om$core$IRender$render$arity$1 = ((function (map__13214,map__13214__$1,departments){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return cljs.core.apply.call(null,om.dom.ul,null,om.core.build_all.call(null,dashboard.core.department_name,self__.departments));
});})(map__13214,map__13214__$1,departments))
;
dashboard.core.t13215.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__13214,map__13214__$1,departments){
return (function (_13217){var self__ = this;
var _13217__$1 = this;return self__.meta13216;
});})(map__13214,map__13214__$1,departments))
;
dashboard.core.t13215.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__13214,map__13214__$1,departments){
return (function (_13217,meta13216__$1){var self__ = this;
var _13217__$1 = this;return (new dashboard.core.t13215(self__.departments,self__.map__13214,self__.p__13209,self__.department_list,meta13216__$1));
});})(map__13214,map__13214__$1,departments))
;
dashboard.core.__GT_t13215 = ((function (map__13214,map__13214__$1,departments){
return (function __GT_t13215(departments__$1,map__13214__$2,p__13209__$1,department_list__$1,meta13216){return (new dashboard.core.t13215(departments__$1,map__13214__$2,p__13209__$1,department_list__$1,meta13216));
});})(map__13214,map__13214__$1,departments))
;
}
return (new dashboard.core.t13215(departments,map__13214__$1,p__13209,department_list,null));
});
dashboard.core.departments_box = (function departments_box(app,owner,opts){if(typeof dashboard.core.t13262 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13262 = (function (opts,owner,app,departments_box,meta13263){
this.opts = opts;
this.owner = owner;
this.app = app;
this.departments_box = departments_box;
this.meta13263 = meta13263;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13262.cljs$lang$type = true;
dashboard.core.t13262.cljs$lang$ctorStr = "dashboard.core/t13262";
dashboard.core.t13262.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13262");
});
dashboard.core.t13262.prototype.om$core$IRenderState$ = true;
dashboard.core.t13262.prototype.om$core$IRenderState$render_state$arity$2 = (function (_,p__13265){var self__ = this;
var map__13266 = p__13265;var map__13266__$1 = ((cljs.core.seq_QMARK_.call(null,map__13266))?cljs.core.apply.call(null,cljs.core.hash_map,map__13266):map__13266);var departments = cljs.core.get.call(null,map__13266__$1,new cljs.core.Keyword(null,"departments","departments",799278298));var ___$1 = this;console.log(cljs.core.println.call(null,"departments in app:",departments));
return React.DOM.h1(null,"Departments");
});
dashboard.core.t13262.prototype.om$core$IWillMount$ = true;
dashboard.core.t13262.prototype.om$core$IWillMount$will_mount$arity$1 = (function (_){var self__ = this;
var ___$1 = this;var c__6424__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6424__auto__,___$1){
return (function (){var f__6425__auto__ = (function (){var switch__6409__auto__ = ((function (c__6424__auto__,___$1){
return (function (state_13288){var state_val_13289 = (state_13288[(1)]);if((state_val_13289 === (8)))
{var inst_13280 = (state_13288[(2)]);var state_13288__$1 = (function (){var statearr_13290 = state_13288;(statearr_13290[(7)] = inst_13280);
return statearr_13290;
})();var statearr_13291_13305 = state_13288__$1;(statearr_13291_13305[(2)] = null);
(statearr_13291_13305[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13289 === (7)))
{var inst_13272 = (state_13288[(2)]);var inst_13273 = cljs.core.println.call(null,"xx:",inst_13272);var inst_13274 = console.log(inst_13273);var inst_13275 = (function (){var departments = inst_13272;return ((function (departments,inst_13272,inst_13273,inst_13274,state_val_13289,c__6424__auto__,___$1){
return (function (p1__13218_SHARP_){return cljs.core.conj.call(null,p1__13218_SHARP_,departments);
});
;})(departments,inst_13272,inst_13273,inst_13274,state_val_13289,c__6424__auto__,___$1))
})();var inst_13276 = om.core.transact_BANG_.call(null,self__.app,new cljs.core.Keyword(null,"departments","departments",799278298),inst_13275);var inst_13277 = new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570).cljs$core$IFn$_invoke$arity$1(self__.opts);var inst_13278 = cljs.core.async.timeout.call(null,inst_13277);var state_13288__$1 = (function (){var statearr_13292 = state_13288;(statearr_13292[(8)] = inst_13276);
(statearr_13292[(9)] = inst_13274);
return statearr_13292;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13288__$1,(8),inst_13278);
} else
{if((state_val_13289 === (6)))
{var inst_13284 = (state_13288[(2)]);var state_13288__$1 = state_13288;var statearr_13293_13306 = state_13288__$1;(statearr_13293_13306[(2)] = inst_13284);
(statearr_13293_13306[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13289 === (5)))
{var state_13288__$1 = state_13288;var statearr_13294_13307 = state_13288__$1;(statearr_13294_13307[(2)] = null);
(statearr_13294_13307[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13289 === (4)))
{var inst_13269 = new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(self__.opts);var inst_13270 = dashboard.core.fetch_departments.call(null,inst_13269);var state_13288__$1 = state_13288;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13288__$1,(7),inst_13270);
} else
{if((state_val_13289 === (3)))
{var inst_13286 = (state_13288[(2)]);var state_13288__$1 = state_13288;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13288__$1,inst_13286);
} else
{if((state_val_13289 === (2)))
{var state_13288__$1 = state_13288;var statearr_13295_13308 = state_13288__$1;(statearr_13295_13308[(1)] = (4));

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13289 === (1)))
{var state_13288__$1 = state_13288;var statearr_13297_13309 = state_13288__$1;(statearr_13297_13309[(2)] = null);
(statearr_13297_13309[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{return null;
}
}
}
}
}
}
}
}
});})(c__6424__auto__,___$1))
;return ((function (switch__6409__auto__,c__6424__auto__,___$1){
return (function() {
var state_machine__6410__auto__ = null;
var state_machine__6410__auto____0 = (function (){var statearr_13301 = [null,null,null,null,null,null,null,null,null,null];(statearr_13301[(0)] = state_machine__6410__auto__);
(statearr_13301[(1)] = (1));
return statearr_13301;
});
var state_machine__6410__auto____1 = (function (state_13288){while(true){
var ret_value__6411__auto__ = (function (){try{while(true){
var result__6412__auto__ = switch__6409__auto__.call(null,state_13288);if(cljs.core.keyword_identical_QMARK_.call(null,result__6412__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6412__auto__;
}
break;
}
}catch (e13302){if((e13302 instanceof Object))
{var ex__6413__auto__ = e13302;var statearr_13303_13310 = state_13288;(statearr_13303_13310[(5)] = ex__6413__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13288);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13302;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6411__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13311 = state_13288;
state_13288 = G__13311;
continue;
}
} else
{return ret_value__6411__auto__;
}
break;
}
});
state_machine__6410__auto__ = function(state_13288){
switch(arguments.length){
case 0:
return state_machine__6410__auto____0.call(this);
case 1:
return state_machine__6410__auto____1.call(this,state_13288);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6410__auto____0;
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6410__auto____1;
return state_machine__6410__auto__;
})()
;})(switch__6409__auto__,c__6424__auto__,___$1))
})();var state__6426__auto__ = (function (){var statearr_13304 = f__6425__auto__.call(null);(statearr_13304[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6424__auto__);
return statearr_13304;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6426__auto__);
});})(c__6424__auto__,___$1))
);
return c__6424__auto__;
});
dashboard.core.t13262.prototype.om$core$IInitState$ = true;
dashboard.core.t13262.prototype.om$core$IInitState$init_state$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"departments","departments",799278298),cljs.core.PersistentVector.EMPTY], null);
});
dashboard.core.t13262.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_13264){var self__ = this;
var _13264__$1 = this;return self__.meta13263;
});
dashboard.core.t13262.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_13264,meta13263__$1){var self__ = this;
var _13264__$1 = this;return (new dashboard.core.t13262(self__.opts,self__.owner,self__.app,self__.departments_box,meta13263__$1));
});
dashboard.core.__GT_t13262 = (function __GT_t13262(opts__$1,owner__$1,app__$1,departments_box__$1,meta13263){return (new dashboard.core.t13262(opts__$1,owner__$1,app__$1,departments_box__$1,meta13263));
});
}
return (new dashboard.core.t13262(opts,owner,app,departments_box,null));
});
dashboard.core.om_app = (function om_app(app,owner){if(typeof dashboard.core.t13315 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13315 = (function (owner,app,om_app,meta13316){
this.owner = owner;
this.app = app;
this.om_app = om_app;
this.meta13316 = meta13316;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13315.cljs$lang$type = true;
dashboard.core.t13315.cljs$lang$ctorStr = "dashboard.core/t13315";
dashboard.core.t13315.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13315");
});
dashboard.core.t13315.prototype.om$core$IRender$ = true;
dashboard.core.t13315.prototype.om$core$IRender$render$arity$1 = (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.div(null,om.core.build.call(null,dashboard.core.departments_box,self__.app,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"opts","opts",155075701),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"url","url",276297046),"http://localhost:3030/api/departments",new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570),(2000)], null)], null)));
});
dashboard.core.t13315.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_13317){var self__ = this;
var _13317__$1 = this;return self__.meta13316;
});
dashboard.core.t13315.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_13317,meta13316__$1){var self__ = this;
var _13317__$1 = this;return (new dashboard.core.t13315(self__.owner,self__.app,self__.om_app,meta13316__$1));
});
dashboard.core.__GT_t13315 = (function __GT_t13315(owner__$1,app__$1,om_app__$1,meta13316){return (new dashboard.core.t13315(owner__$1,app__$1,om_app__$1,meta13316));
});
}
return (new dashboard.core.t13315(owner,app,om_app,null));
});
om.core.root.call(null,dashboard.core.om_app,dashboard.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",253001721),document.getElementById("main-container")], null));

//# sourceMappingURL=core.js.map
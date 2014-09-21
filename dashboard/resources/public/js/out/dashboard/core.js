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
dashboard.core.fetch_departments = (function fetch_departments(url){var c = cljs.core.async.chan.call(null);var c__6424__auto___13362 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6424__auto___13362,c){
return (function (){var f__6425__auto__ = (function (){var switch__6409__auto__ = ((function (c__6424__auto___13362,c){
return (function (state_13353){var state_val_13354 = (state_13353[(1)]);if((state_val_13354 === (2)))
{var inst_13351 = (state_13353[(2)]);var state_13353__$1 = state_13353;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13353__$1,inst_13351);
} else
{if((state_val_13354 === (1)))
{var inst_13340 = cljs.core.PersistentVector.EMPTY_NODE;var inst_13341 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_13342 = ["Department 1"];var inst_13343 = cljs.core.PersistentHashMap.fromArrays(inst_13341,inst_13342);var inst_13344 = [new cljs.core.Keyword(null,"department","department",-359157087)];var inst_13345 = ["Department 2"];var inst_13346 = cljs.core.PersistentHashMap.fromArrays(inst_13344,inst_13345);var inst_13347 = [inst_13343,inst_13346];var inst_13348 = (new cljs.core.PersistentVector(null,2,(5),inst_13340,inst_13347,null));var inst_13349 = cljs.core.vec.call(null,inst_13348);var state_13353__$1 = state_13353;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_13353__$1,(2),c,inst_13349);
} else
{return null;
}
}
});})(c__6424__auto___13362,c))
;return ((function (switch__6409__auto__,c__6424__auto___13362,c){
return (function() {
var state_machine__6410__auto__ = null;
var state_machine__6410__auto____0 = (function (){var statearr_13358 = [null,null,null,null,null,null,null];(statearr_13358[(0)] = state_machine__6410__auto__);
(statearr_13358[(1)] = (1));
return statearr_13358;
});
var state_machine__6410__auto____1 = (function (state_13353){while(true){
var ret_value__6411__auto__ = (function (){try{while(true){
var result__6412__auto__ = switch__6409__auto__.call(null,state_13353);if(cljs.core.keyword_identical_QMARK_.call(null,result__6412__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6412__auto__;
}
break;
}
}catch (e13359){if((e13359 instanceof Object))
{var ex__6413__auto__ = e13359;var statearr_13360_13363 = state_13353;(statearr_13360_13363[(5)] = ex__6413__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13353);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13359;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6411__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13364 = state_13353;
state_13353 = G__13364;
continue;
}
} else
{return ret_value__6411__auto__;
}
break;
}
});
state_machine__6410__auto__ = function(state_13353){
switch(arguments.length){
case 0:
return state_machine__6410__auto____0.call(this);
case 1:
return state_machine__6410__auto____1.call(this,state_13353);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6410__auto____0;
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6410__auto____1;
return state_machine__6410__auto__;
})()
;})(switch__6409__auto__,c__6424__auto___13362,c))
})();var state__6426__auto__ = (function (){var statearr_13361 = f__6425__auto__.call(null);(statearr_13361[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6424__auto___13362);
return statearr_13361;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6426__auto__);
});})(c__6424__auto___13362,c))
);
return c;
});
dashboard.core.department_name = (function department_name(p__13365,owner,opts){var map__13370 = p__13365;var map__13370__$1 = ((cljs.core.seq_QMARK_.call(null,map__13370))?cljs.core.apply.call(null,cljs.core.hash_map,map__13370):map__13370);var department = cljs.core.get.call(null,map__13370__$1,new cljs.core.Keyword(null,"department","department",-359157087));if(typeof dashboard.core.t13371 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13371 = (function (department,map__13370,opts,owner,p__13365,department_name,meta13372){
this.department = department;
this.map__13370 = map__13370;
this.opts = opts;
this.owner = owner;
this.p__13365 = p__13365;
this.department_name = department_name;
this.meta13372 = meta13372;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13371.cljs$lang$type = true;
dashboard.core.t13371.cljs$lang$ctorStr = "dashboard.core/t13371";
dashboard.core.t13371.cljs$lang$ctorPrWriter = ((function (map__13370,map__13370__$1,department){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13371");
});})(map__13370,map__13370__$1,department))
;
dashboard.core.t13371.prototype.om$core$IRender$ = true;
dashboard.core.t13371.prototype.om$core$IRender$render$arity$1 = ((function (map__13370,map__13370__$1,department){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.li(null,React.DOM.span(null,"hello !!!!!"));
});})(map__13370,map__13370__$1,department))
;
dashboard.core.t13371.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__13370,map__13370__$1,department){
return (function (_13373){var self__ = this;
var _13373__$1 = this;return self__.meta13372;
});})(map__13370,map__13370__$1,department))
;
dashboard.core.t13371.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__13370,map__13370__$1,department){
return (function (_13373,meta13372__$1){var self__ = this;
var _13373__$1 = this;return (new dashboard.core.t13371(self__.department,self__.map__13370,self__.opts,self__.owner,self__.p__13365,self__.department_name,meta13372__$1));
});})(map__13370,map__13370__$1,department))
;
dashboard.core.__GT_t13371 = ((function (map__13370,map__13370__$1,department){
return (function __GT_t13371(department__$1,map__13370__$2,opts__$1,owner__$1,p__13365__$1,department_name__$1,meta13372){return (new dashboard.core.t13371(department__$1,map__13370__$2,opts__$1,owner__$1,p__13365__$1,department_name__$1,meta13372));
});})(map__13370,map__13370__$1,department))
;
}
return (new dashboard.core.t13371(department,map__13370__$1,opts,owner,p__13365,department_name,null));
});
dashboard.core.department_list = (function department_list(p__13374){var map__13379 = p__13374;var map__13379__$1 = ((cljs.core.seq_QMARK_.call(null,map__13379))?cljs.core.apply.call(null,cljs.core.hash_map,map__13379):map__13379);var departments = cljs.core.get.call(null,map__13379__$1,new cljs.core.Keyword(null,"departments","departments",799278298));console.log(cljs.core.println.call(null,"list of departments:",departments));
if(typeof dashboard.core.t13380 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13380 = (function (departments,map__13379,p__13374,department_list,meta13381){
this.departments = departments;
this.map__13379 = map__13379;
this.p__13374 = p__13374;
this.department_list = department_list;
this.meta13381 = meta13381;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13380.cljs$lang$type = true;
dashboard.core.t13380.cljs$lang$ctorStr = "dashboard.core/t13380";
dashboard.core.t13380.cljs$lang$ctorPrWriter = ((function (map__13379,map__13379__$1,departments){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13380");
});})(map__13379,map__13379__$1,departments))
;
dashboard.core.t13380.prototype.om$core$IRender$ = true;
dashboard.core.t13380.prototype.om$core$IRender$render$arity$1 = ((function (map__13379,map__13379__$1,departments){
return (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return cljs.core.apply.call(null,om.dom.ul,null,om.core.build_all.call(null,dashboard.core.department_name,self__.departments));
});})(map__13379,map__13379__$1,departments))
;
dashboard.core.t13380.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__13379,map__13379__$1,departments){
return (function (_13382){var self__ = this;
var _13382__$1 = this;return self__.meta13381;
});})(map__13379,map__13379__$1,departments))
;
dashboard.core.t13380.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__13379,map__13379__$1,departments){
return (function (_13382,meta13381__$1){var self__ = this;
var _13382__$1 = this;return (new dashboard.core.t13380(self__.departments,self__.map__13379,self__.p__13374,self__.department_list,meta13381__$1));
});})(map__13379,map__13379__$1,departments))
;
dashboard.core.__GT_t13380 = ((function (map__13379,map__13379__$1,departments){
return (function __GT_t13380(departments__$1,map__13379__$2,p__13374__$1,department_list__$1,meta13381){return (new dashboard.core.t13380(departments__$1,map__13379__$2,p__13374__$1,department_list__$1,meta13381));
});})(map__13379,map__13379__$1,departments))
;
}
return (new dashboard.core.t13380(departments,map__13379__$1,p__13374,department_list,null));
});
dashboard.core.departments_box = (function departments_box(app,owner,opts){if(typeof dashboard.core.t13427 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13427 = (function (opts,owner,app,departments_box,meta13428){
this.opts = opts;
this.owner = owner;
this.app = app;
this.departments_box = departments_box;
this.meta13428 = meta13428;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13427.cljs$lang$type = true;
dashboard.core.t13427.cljs$lang$ctorStr = "dashboard.core/t13427";
dashboard.core.t13427.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13427");
});
dashboard.core.t13427.prototype.om$core$IRenderState$ = true;
dashboard.core.t13427.prototype.om$core$IRenderState$render_state$arity$2 = (function (_,p__13430){var self__ = this;
var map__13431 = p__13430;var map__13431__$1 = ((cljs.core.seq_QMARK_.call(null,map__13431))?cljs.core.apply.call(null,cljs.core.hash_map,map__13431):map__13431);var departments = cljs.core.get.call(null,map__13431__$1,new cljs.core.Keyword(null,"departments","departments",799278298));var ___$1 = this;console.log(cljs.core.println.call(null,"departments in app:",departments));
return React.DOM.h1(null,"Departments");
});
dashboard.core.t13427.prototype.om$core$IWillMount$ = true;
dashboard.core.t13427.prototype.om$core$IWillMount$will_mount$arity$1 = (function (_){var self__ = this;
var ___$1 = this;var c__6424__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6424__auto__,___$1){
return (function (){var f__6425__auto__ = (function (){var switch__6409__auto__ = ((function (c__6424__auto__,___$1){
return (function (state_13453){var state_val_13454 = (state_13453[(1)]);if((state_val_13454 === (8)))
{var inst_13445 = (state_13453[(2)]);var state_13453__$1 = (function (){var statearr_13455 = state_13453;(statearr_13455[(7)] = inst_13445);
return statearr_13455;
})();var statearr_13456_13470 = state_13453__$1;(statearr_13456_13470[(2)] = null);
(statearr_13456_13470[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13454 === (7)))
{var inst_13437 = (state_13453[(2)]);var inst_13438 = cljs.core.println.call(null,"xx:",inst_13437);var inst_13439 = console.log(inst_13438);var inst_13440 = (function (){var departments = inst_13437;return ((function (departments,inst_13437,inst_13438,inst_13439,state_val_13454,c__6424__auto__,___$1){
return (function (p1__13383_SHARP_){return cljs.core.conj.call(null,p1__13383_SHARP_,departments);
});
;})(departments,inst_13437,inst_13438,inst_13439,state_val_13454,c__6424__auto__,___$1))
})();var inst_13441 = om.core.transact_BANG_.call(null,self__.app,new cljs.core.Keyword(null,"departments","departments",799278298),inst_13440);var inst_13442 = new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570).cljs$core$IFn$_invoke$arity$1(self__.opts);var inst_13443 = cljs.core.async.timeout.call(null,inst_13442);var state_13453__$1 = (function (){var statearr_13457 = state_13453;(statearr_13457[(8)] = inst_13441);
(statearr_13457[(9)] = inst_13439);
return statearr_13457;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13453__$1,(8),inst_13443);
} else
{if((state_val_13454 === (6)))
{var inst_13449 = (state_13453[(2)]);var state_13453__$1 = state_13453;var statearr_13458_13471 = state_13453__$1;(statearr_13458_13471[(2)] = inst_13449);
(statearr_13458_13471[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13454 === (5)))
{var state_13453__$1 = state_13453;var statearr_13459_13472 = state_13453__$1;(statearr_13459_13472[(2)] = null);
(statearr_13459_13472[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13454 === (4)))
{var inst_13434 = new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(self__.opts);var inst_13435 = dashboard.core.fetch_departments.call(null,inst_13434);var state_13453__$1 = state_13453;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_13453__$1,(7),inst_13435);
} else
{if((state_val_13454 === (3)))
{var inst_13451 = (state_13453[(2)]);var state_13453__$1 = state_13453;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_13453__$1,inst_13451);
} else
{if((state_val_13454 === (2)))
{var state_13453__$1 = state_13453;var statearr_13460_13473 = state_13453__$1;(statearr_13460_13473[(1)] = (4));

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_13454 === (1)))
{var state_13453__$1 = state_13453;var statearr_13462_13474 = state_13453__$1;(statearr_13462_13474[(2)] = null);
(statearr_13462_13474[(1)] = (2));
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
var state_machine__6410__auto____0 = (function (){var statearr_13466 = [null,null,null,null,null,null,null,null,null,null];(statearr_13466[(0)] = state_machine__6410__auto__);
(statearr_13466[(1)] = (1));
return statearr_13466;
});
var state_machine__6410__auto____1 = (function (state_13453){while(true){
var ret_value__6411__auto__ = (function (){try{while(true){
var result__6412__auto__ = switch__6409__auto__.call(null,state_13453);if(cljs.core.keyword_identical_QMARK_.call(null,result__6412__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6412__auto__;
}
break;
}
}catch (e13467){if((e13467 instanceof Object))
{var ex__6413__auto__ = e13467;var statearr_13468_13475 = state_13453;(statearr_13468_13475[(5)] = ex__6413__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_13453);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e13467;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6411__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__13476 = state_13453;
state_13453 = G__13476;
continue;
}
} else
{return ret_value__6411__auto__;
}
break;
}
});
state_machine__6410__auto__ = function(state_13453){
switch(arguments.length){
case 0:
return state_machine__6410__auto____0.call(this);
case 1:
return state_machine__6410__auto____1.call(this,state_13453);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6410__auto____0;
state_machine__6410__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6410__auto____1;
return state_machine__6410__auto__;
})()
;})(switch__6409__auto__,c__6424__auto__,___$1))
})();var state__6426__auto__ = (function (){var statearr_13469 = f__6425__auto__.call(null);(statearr_13469[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6424__auto__);
return statearr_13469;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6426__auto__);
});})(c__6424__auto__,___$1))
);
return c__6424__auto__;
});
dashboard.core.t13427.prototype.om$core$IInitState$ = true;
dashboard.core.t13427.prototype.om$core$IInitState$init_state$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"departments","departments",799278298),cljs.core.PersistentVector.EMPTY], null);
});
dashboard.core.t13427.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_13429){var self__ = this;
var _13429__$1 = this;return self__.meta13428;
});
dashboard.core.t13427.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_13429,meta13428__$1){var self__ = this;
var _13429__$1 = this;return (new dashboard.core.t13427(self__.opts,self__.owner,self__.app,self__.departments_box,meta13428__$1));
});
dashboard.core.__GT_t13427 = (function __GT_t13427(opts__$1,owner__$1,app__$1,departments_box__$1,meta13428){return (new dashboard.core.t13427(opts__$1,owner__$1,app__$1,departments_box__$1,meta13428));
});
}
return (new dashboard.core.t13427(opts,owner,app,departments_box,null));
});
dashboard.core.om_app = (function om_app(app,owner){if(typeof dashboard.core.t13480 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t13480 = (function (owner,app,om_app,meta13481){
this.owner = owner;
this.app = app;
this.om_app = om_app;
this.meta13481 = meta13481;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t13480.cljs$lang$type = true;
dashboard.core.t13480.cljs$lang$ctorStr = "dashboard.core/t13480";
dashboard.core.t13480.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t13480");
});
dashboard.core.t13480.prototype.om$core$IRender$ = true;
dashboard.core.t13480.prototype.om$core$IRender$render$arity$1 = (function (this__5186__auto__){var self__ = this;
var this__5186__auto____$1 = this;return React.DOM.div(null,om.core.build.call(null,dashboard.core.departments_box,self__.app,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"opts","opts",155075701),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"url","url",276297046),"http://localhost:3030/api/departments",new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570),(2000)], null)], null)));
});
dashboard.core.t13480.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_13482){var self__ = this;
var _13482__$1 = this;return self__.meta13481;
});
dashboard.core.t13480.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_13482,meta13481__$1){var self__ = this;
var _13482__$1 = this;return (new dashboard.core.t13480(self__.owner,self__.app,self__.om_app,meta13481__$1));
});
dashboard.core.__GT_t13480 = (function __GT_t13480(owner__$1,app__$1,om_app__$1,meta13481){return (new dashboard.core.t13480(owner__$1,app__$1,om_app__$1,meta13481));
});
}
return (new dashboard.core.t13480(owner,app,om_app,null));
});
om.core.root.call(null,dashboard.core.om_app,dashboard.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",253001721),document.getElementById("main-container")], null));

//# sourceMappingURL=core.js.map
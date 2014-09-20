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
dashboard.core.fetch_departments = (function fetch_departments(url){var c = cljs.core.async.chan.call(null);var c__6419__auto___10304 = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto___10304,c){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto___10304,c){
return (function (state_10290){var state_val_10291 = (state_10290[(1)]);if((state_val_10291 === (6)))
{var inst_10288 = (state_10290[(2)]);var state_10290__$1 = state_10290;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10290__$1,inst_10288);
} else
{if((state_val_10291 === (5)))
{var inst_10284 = (state_10290[(2)]);var inst_10285 = cljs.core.get.call(null,inst_10284,new cljs.core.Keyword(null,"body","body",-2049205669));var inst_10286 = cljs.core.vec.call(null,inst_10285);var state_10290__$1 = state_10290;return cljs.core.async.impl.ioc_helpers.put_BANG_.call(null,state_10290__$1,(6),c,inst_10286);
} else
{if((state_val_10291 === (4)))
{var inst_10278 = (state_10290[(7)]);var state_10290__$1 = state_10290;var statearr_10292_10305 = state_10290__$1;(statearr_10292_10305[(2)] = inst_10278);
(statearr_10292_10305[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10291 === (3)))
{var inst_10278 = (state_10290[(7)]);var inst_10281 = cljs.core.apply.call(null,cljs.core.hash_map,inst_10278);var state_10290__$1 = state_10290;var statearr_10293_10306 = state_10290__$1;(statearr_10293_10306[(2)] = inst_10281);
(statearr_10293_10306[(1)] = (5));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10291 === (2)))
{var inst_10278 = (state_10290[(7)]);var inst_10278__$1 = (state_10290[(2)]);var inst_10279 = cljs.core.seq_QMARK_.call(null,inst_10278__$1);var state_10290__$1 = (function (){var statearr_10294 = state_10290;(statearr_10294[(7)] = inst_10278__$1);
return statearr_10294;
})();if(inst_10279)
{var statearr_10295_10307 = state_10290__$1;(statearr_10295_10307[(1)] = (3));
} else
{var statearr_10296_10308 = state_10290__$1;(statearr_10296_10308[(1)] = (4));
}
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10291 === (1)))
{var inst_10276 = cljs_http.client.get.call(null,url);var state_10290__$1 = state_10290;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10290__$1,(2),inst_10276);
} else
{return null;
}
}
}
}
}
}
});})(c__6419__auto___10304,c))
;return ((function (switch__6404__auto__,c__6419__auto___10304,c){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_10300 = [null,null,null,null,null,null,null,null];(statearr_10300[(0)] = state_machine__6405__auto__);
(statearr_10300[(1)] = (1));
return statearr_10300;
});
var state_machine__6405__auto____1 = (function (state_10290){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_10290);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e10301){if((e10301 instanceof Object))
{var ex__6408__auto__ = e10301;var statearr_10302_10309 = state_10290;(statearr_10302_10309[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10290);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10301;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10310 = state_10290;
state_10290 = G__10310;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_10290){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_10290);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto___10304,c))
})();var state__6421__auto__ = (function (){var statearr_10303 = f__6420__auto__.call(null);(statearr_10303[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto___10304);
return statearr_10303;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto___10304,c))
);
return c;
});
dashboard.core.department = (function department(p__10311,owner,opts){var map__10316 = p__10311;var map__10316__$1 = ((cljs.core.seq_QMARK_.call(null,map__10316))?cljs.core.apply.call(null,cljs.core.hash_map,map__10316):map__10316);var department__$1 = cljs.core.get.call(null,map__10316__$1,new cljs.core.Keyword(null,"department","department",-359157087));if(typeof dashboard.core.t10317 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10317 = (function (map__10316,opts,owner,p__10311,department,meta10318){
this.map__10316 = map__10316;
this.opts = opts;
this.owner = owner;
this.p__10311 = p__10311;
this.department = department;
this.meta10318 = meta10318;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10317.cljs$lang$type = true;
dashboard.core.t10317.cljs$lang$ctorStr = "dashboard.core/t10317";
dashboard.core.t10317.cljs$lang$ctorPrWriter = ((function (map__10316,map__10316__$1,department__$1){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10317");
});})(map__10316,map__10316__$1,department__$1))
;
dashboard.core.t10317.prototype.om$core$IRender$ = true;
dashboard.core.t10317.prototype.om$core$IRender$render$arity$1 = ((function (map__10316,map__10316__$1,department__$1){
return (function (this__5181__auto__){var self__ = this;
var this__5181__auto____$1 = this;return React.DOM.li(null,self__.department);
});})(map__10316,map__10316__$1,department__$1))
;
dashboard.core.t10317.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__10316,map__10316__$1,department__$1){
return (function (_10319){var self__ = this;
var _10319__$1 = this;return self__.meta10318;
});})(map__10316,map__10316__$1,department__$1))
;
dashboard.core.t10317.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__10316,map__10316__$1,department__$1){
return (function (_10319,meta10318__$1){var self__ = this;
var _10319__$1 = this;return (new dashboard.core.t10317(self__.map__10316,self__.opts,self__.owner,self__.p__10311,self__.department,meta10318__$1));
});})(map__10316,map__10316__$1,department__$1))
;
dashboard.core.__GT_t10317 = ((function (map__10316,map__10316__$1,department__$1){
return (function __GT_t10317(map__10316__$2,opts__$1,owner__$1,p__10311__$1,department__$2,meta10318){return (new dashboard.core.t10317(map__10316__$2,opts__$1,owner__$1,p__10311__$1,department__$2,meta10318));
});})(map__10316,map__10316__$1,department__$1))
;
}
return (new dashboard.core.t10317(map__10316__$1,opts,owner,p__10311,department__$1,null));
});
dashboard.core.department_list = (function department_list(p__10320){var map__10325 = p__10320;var map__10325__$1 = ((cljs.core.seq_QMARK_.call(null,map__10325))?cljs.core.apply.call(null,cljs.core.hash_map,map__10325):map__10325);var departments = cljs.core.get.call(null,map__10325__$1,new cljs.core.Keyword(null,"departments","departments",799278298));if(typeof dashboard.core.t10326 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10326 = (function (departments,map__10325,p__10320,department_list,meta10327){
this.departments = departments;
this.map__10325 = map__10325;
this.p__10320 = p__10320;
this.department_list = department_list;
this.meta10327 = meta10327;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10326.cljs$lang$type = true;
dashboard.core.t10326.cljs$lang$ctorStr = "dashboard.core/t10326";
dashboard.core.t10326.cljs$lang$ctorPrWriter = ((function (map__10325,map__10325__$1,departments){
return (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10326");
});})(map__10325,map__10325__$1,departments))
;
dashboard.core.t10326.prototype.om$core$IRender$ = true;
dashboard.core.t10326.prototype.om$core$IRender$render$arity$1 = ((function (map__10325,map__10325__$1,departments){
return (function (this__5181__auto__){var self__ = this;
var this__5181__auto____$1 = this;return cljs.core.apply.call(null,om.dom.ul,null,om.core.build_all.call(null,dashboard.core.department,self__.departments));
});})(map__10325,map__10325__$1,departments))
;
dashboard.core.t10326.prototype.cljs$core$IMeta$_meta$arity$1 = ((function (map__10325,map__10325__$1,departments){
return (function (_10328){var self__ = this;
var _10328__$1 = this;return self__.meta10327;
});})(map__10325,map__10325__$1,departments))
;
dashboard.core.t10326.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = ((function (map__10325,map__10325__$1,departments){
return (function (_10328,meta10327__$1){var self__ = this;
var _10328__$1 = this;return (new dashboard.core.t10326(self__.departments,self__.map__10325,self__.p__10320,self__.department_list,meta10327__$1));
});})(map__10325,map__10325__$1,departments))
;
dashboard.core.__GT_t10326 = ((function (map__10325,map__10325__$1,departments){
return (function __GT_t10326(departments__$1,map__10325__$2,p__10320__$1,department_list__$1,meta10327){return (new dashboard.core.t10326(departments__$1,map__10325__$2,p__10320__$1,department_list__$1,meta10327));
});})(map__10325,map__10325__$1,departments))
;
}
return (new dashboard.core.t10326(departments,map__10325__$1,p__10320,department_list,null));
});
dashboard.core.departments_box = (function departments_box(app,owner,opts){if(typeof dashboard.core.t10368 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10368 = (function (opts,owner,app,departments_box,meta10369){
this.opts = opts;
this.owner = owner;
this.app = app;
this.departments_box = departments_box;
this.meta10369 = meta10369;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10368.cljs$lang$type = true;
dashboard.core.t10368.cljs$lang$ctorStr = "dashboard.core/t10368";
dashboard.core.t10368.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10368");
});
dashboard.core.t10368.prototype.om$core$IRender$ = true;
dashboard.core.t10368.prototype.om$core$IRender$render$arity$1 = (function (_){var self__ = this;
var ___$1 = this;React.DOM.h1(null,"Departments");
return om.core.build.call(null,dashboard.core.department_list,self__.app);
});
dashboard.core.t10368.prototype.om$core$IWillMount$ = true;
dashboard.core.t10368.prototype.om$core$IWillMount$will_mount$arity$1 = (function (_){var self__ = this;
var ___$1 = this;var c__6419__auto__ = cljs.core.async.chan.call(null,(1));cljs.core.async.impl.dispatch.run.call(null,((function (c__6419__auto__,___$1){
return (function (){var f__6420__auto__ = (function (){var switch__6404__auto__ = ((function (c__6419__auto__,___$1){
return (function (state_10390){var state_val_10391 = (state_10390[(1)]);if((state_val_10391 === (8)))
{var inst_10382 = (state_10390[(2)]);var state_10390__$1 = (function (){var statearr_10392 = state_10390;(statearr_10392[(7)] = inst_10382);
return statearr_10392;
})();var statearr_10393_10407 = state_10390__$1;(statearr_10393_10407[(2)] = null);
(statearr_10393_10407[(1)] = (2));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10391 === (7)))
{var inst_10376 = (state_10390[(2)]);var inst_10377 = cljs.core.pr_str.call(null,inst_10376);var inst_10378 = console.log(inst_10377);var inst_10379 = new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570).cljs$core$IFn$_invoke$arity$1(self__.opts);var inst_10380 = cljs.core.async.timeout.call(null,inst_10379);var state_10390__$1 = (function (){var statearr_10394 = state_10390;(statearr_10394[(8)] = inst_10378);
return statearr_10394;
})();return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10390__$1,(8),inst_10380);
} else
{if((state_val_10391 === (6)))
{var inst_10386 = (state_10390[(2)]);var state_10390__$1 = state_10390;var statearr_10395_10408 = state_10390__$1;(statearr_10395_10408[(2)] = inst_10386);
(statearr_10395_10408[(1)] = (3));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10391 === (5)))
{var state_10390__$1 = state_10390;var statearr_10396_10409 = state_10390__$1;(statearr_10396_10409[(2)] = null);
(statearr_10396_10409[(1)] = (6));
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10391 === (4)))
{var inst_10373 = new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(self__.opts);var inst_10374 = dashboard.core.fetch_departments.call(null,inst_10373);var state_10390__$1 = state_10390;return cljs.core.async.impl.ioc_helpers.take_BANG_.call(null,state_10390__$1,(7),inst_10374);
} else
{if((state_val_10391 === (3)))
{var inst_10388 = (state_10390[(2)]);var state_10390__$1 = state_10390;return cljs.core.async.impl.ioc_helpers.return_chan.call(null,state_10390__$1,inst_10388);
} else
{if((state_val_10391 === (2)))
{var state_10390__$1 = state_10390;var statearr_10397_10410 = state_10390__$1;(statearr_10397_10410[(1)] = (4));

return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{if((state_val_10391 === (1)))
{var state_10390__$1 = state_10390;var statearr_10399_10411 = state_10390__$1;(statearr_10399_10411[(2)] = null);
(statearr_10399_10411[(1)] = (2));
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
});})(c__6419__auto__,___$1))
;return ((function (switch__6404__auto__,c__6419__auto__,___$1){
return (function() {
var state_machine__6405__auto__ = null;
var state_machine__6405__auto____0 = (function (){var statearr_10403 = [null,null,null,null,null,null,null,null,null];(statearr_10403[(0)] = state_machine__6405__auto__);
(statearr_10403[(1)] = (1));
return statearr_10403;
});
var state_machine__6405__auto____1 = (function (state_10390){while(true){
var ret_value__6406__auto__ = (function (){try{while(true){
var result__6407__auto__ = switch__6404__auto__.call(null,state_10390);if(cljs.core.keyword_identical_QMARK_.call(null,result__6407__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
continue;
}
} else
{return result__6407__auto__;
}
break;
}
}catch (e10404){if((e10404 instanceof Object))
{var ex__6408__auto__ = e10404;var statearr_10405_10412 = state_10390;(statearr_10405_10412[(5)] = ex__6408__auto__);
cljs.core.async.impl.ioc_helpers.process_exception.call(null,state_10390);
return new cljs.core.Keyword(null,"recur","recur",-437573268);
} else
{throw e10404;

}
}})();if(cljs.core.keyword_identical_QMARK_.call(null,ret_value__6406__auto__,new cljs.core.Keyword(null,"recur","recur",-437573268)))
{{
var G__10413 = state_10390;
state_10390 = G__10413;
continue;
}
} else
{return ret_value__6406__auto__;
}
break;
}
});
state_machine__6405__auto__ = function(state_10390){
switch(arguments.length){
case 0:
return state_machine__6405__auto____0.call(this);
case 1:
return state_machine__6405__auto____1.call(this,state_10390);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$0 = state_machine__6405__auto____0;
state_machine__6405__auto__.cljs$core$IFn$_invoke$arity$1 = state_machine__6405__auto____1;
return state_machine__6405__auto__;
})()
;})(switch__6404__auto__,c__6419__auto__,___$1))
})();var state__6421__auto__ = (function (){var statearr_10406 = f__6420__auto__.call(null);(statearr_10406[cljs.core.async.impl.ioc_helpers.USER_START_IDX] = c__6419__auto__);
return statearr_10406;
})();return cljs.core.async.impl.ioc_helpers.run_state_machine_wrapped.call(null,state__6421__auto__);
});})(c__6419__auto__,___$1))
);
return c__6419__auto__;
});
dashboard.core.t10368.prototype.om$core$IInitState$ = true;
dashboard.core.t10368.prototype.om$core$IInitState$init_state$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return null;
});
dashboard.core.t10368.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10370){var self__ = this;
var _10370__$1 = this;return self__.meta10369;
});
dashboard.core.t10368.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10370,meta10369__$1){var self__ = this;
var _10370__$1 = this;return (new dashboard.core.t10368(self__.opts,self__.owner,self__.app,self__.departments_box,meta10369__$1));
});
dashboard.core.__GT_t10368 = (function __GT_t10368(opts__$1,owner__$1,app__$1,departments_box__$1,meta10369){return (new dashboard.core.t10368(opts__$1,owner__$1,app__$1,departments_box__$1,meta10369));
});
}
return (new dashboard.core.t10368(opts,owner,app,departments_box,null));
});
dashboard.core.om_app = (function om_app(app,owner){if(typeof dashboard.core.t10417 !== 'undefined')
{} else
{
/**
* @constructor
*/
dashboard.core.t10417 = (function (owner,app,om_app,meta10418){
this.owner = owner;
this.app = app;
this.om_app = om_app;
this.meta10418 = meta10418;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 393216;
})
dashboard.core.t10417.cljs$lang$type = true;
dashboard.core.t10417.cljs$lang$ctorStr = "dashboard.core/t10417";
dashboard.core.t10417.cljs$lang$ctorPrWriter = (function (this__4161__auto__,writer__4162__auto__,opt__4163__auto__){return cljs.core._write.call(null,writer__4162__auto__,"dashboard.core/t10417");
});
dashboard.core.t10417.prototype.om$core$IRender$ = true;
dashboard.core.t10417.prototype.om$core$IRender$render$arity$1 = (function (this__5181__auto__){var self__ = this;
var this__5181__auto____$1 = this;return React.DOM.div(null,om.core.build.call(null,dashboard.core.departments_box,self__.app,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"opts","opts",155075701),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"url","url",276297046),"http://localhost:3030/api/departments",new cljs.core.Keyword(null,"poll-interval","poll-interval",345867570),(2000)], null)], null)));
});
dashboard.core.t10417.prototype.cljs$core$IMeta$_meta$arity$1 = (function (_10419){var self__ = this;
var _10419__$1 = this;return self__.meta10418;
});
dashboard.core.t10417.prototype.cljs$core$IWithMeta$_with_meta$arity$2 = (function (_10419,meta10418__$1){var self__ = this;
var _10419__$1 = this;return (new dashboard.core.t10417(self__.owner,self__.app,self__.om_app,meta10418__$1));
});
dashboard.core.__GT_t10417 = (function __GT_t10417(owner__$1,app__$1,om_app__$1,meta10418){return (new dashboard.core.t10417(owner__$1,app__$1,om_app__$1,meta10418));
});
}
return (new dashboard.core.t10417(owner,app,om_app,null));
});
om.core.root.call(null,dashboard.core.om_app,dashboard.core.app_state,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"target","target",253001721),document.getElementById("main-container")], null));

//# sourceMappingURL=core.js.map
// Compiled by ClojureScript 0.0-2342
goog.provide('cljs_http.core');
goog.require('cljs.core');
goog.require('cljs.core.async');
goog.require('cljs.core.async');
goog.require('cljs_http.util');
goog.require('cljs_http.util');
goog.require('goog.net.XhrIo');
/**
* Execute the HTTP request corresponding to the given Ring request
* map and return a core.async channel.
*/
cljs_http.core.request = (function request(p__11736){var map__11738 = p__11736;var map__11738__$1 = ((cljs.core.seq_QMARK_.call(null,map__11738))?cljs.core.apply.call(null,cljs.core.hash_map,map__11738):map__11738);var request__$1 = map__11738__$1;var body = cljs.core.get.call(null,map__11738__$1,new cljs.core.Keyword(null,"body","body",-2049205669));var headers = cljs.core.get.call(null,map__11738__$1,new cljs.core.Keyword(null,"headers","headers",-835030129));var request_method = cljs.core.get.call(null,map__11738__$1,new cljs.core.Keyword(null,"request-method","request-method",1764796830));var channel = cljs.core.async.chan.call(null);var method = cljs.core.name.call(null,(function (){var or__3585__auto__ = request_method;if(cljs.core.truth_(or__3585__auto__))
{return or__3585__auto__;
} else
{return new cljs.core.Keyword(null,"get","get",1683182755);
}
})());var timeout = (function (){var or__3585__auto__ = new cljs.core.Keyword(null,"timeout","timeout",-318625318).cljs$core$IFn$_invoke$arity$1(request__$1);if(cljs.core.truth_(or__3585__auto__))
{return or__3585__auto__;
} else
{return (0);
}
})();var headers__$1 = cljs_http.util.build_headers.call(null,headers);goog.net.XhrIo.send(cljs_http.util.build_url.call(null,request__$1),((function (channel,method,timeout,headers__$1,map__11738,map__11738__$1,request__$1,body,headers,request_method){
return (function (p1__11735_SHARP_){var target = p1__11735_SHARP_.target;cljs.core.async.put_BANG_.call(null,channel,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"status","status",-1997798413),target.getStatus(),new cljs.core.Keyword(null,"body","body",-2049205669),target.getResponseText(),new cljs.core.Keyword(null,"headers","headers",-835030129),cljs_http.util.parse_headers.call(null,target.getAllResponseHeaders())], null));
return cljs.core.async.close_BANG_.call(null,channel);
});})(channel,method,timeout,headers__$1,map__11738,map__11738__$1,request__$1,body,headers,request_method))
,method,body,headers__$1,timeout,true);
return channel;
});

//# sourceMappingURL=core.js.map
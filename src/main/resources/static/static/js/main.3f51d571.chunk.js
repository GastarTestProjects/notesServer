(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{34:function(t,e,n){t.exports=n(61)},39:function(t,e,n){},61:function(t,e,n){"use strict";n.r(e);var a=n(0),o=n.n(a),c=n(30),r=n.n(c);n(39),Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));var i=n(7),s=n(8),u=n(4),l=n(14),h=n(10),p=n(9),m=n(11),f=function(t){function e(t){var n;return Object(u.a)(this,e),(n=Object(h.a)(this,Object(p.a)(e).call(this,t))).render=function(){return o.a.createElement("div",null,o.a.createElement("div",{className:"note-title"},n.props.note.title),o.a.createElement("div",{className:"note-text"},n.props.note.text),o.a.createElement("div",{className:"note-tags"},n.props.note.tags.map(function(t){return t.name}).join(", ")),o.a.createElement(i.b,{to:"/note/"+n.props.note.id},"Edit"))},n}return Object(m.a)(e,t),e}(o.a.Component),d=n(33),g=n.n(d),v=function(){function t(){Object(u.a)(this,t)}return Object(l.a)(t,null,[{key:"getNotes",value:function(e){t.HTTP.get("notes").then(function(t){return e(t.data)}).catch(function(t){return console.log(t)})}},{key:"getNoteById",value:function(e,n){t.HTTP.get("notes/"+e).then(function(t){return n(t.data)}).catch(function(t){return console.log(t)})}},{key:"saveNote",value:function(e,n){t.HTTP.post("notes",e).then(function(){return n&&n()}).catch(function(t){return console.log(t)})}}]),t}();v.HTTP=g.a.create({baseURL:"http://localhost:8080/"});var b=function(t){function e(t){var n;return Object(u.a)(this,e),(n=Object(h.a)(this,Object(p.a)(e).call(this,t))).getNoteItems=function(){return n.state.notes.map(function(t){return o.a.createElement(f,{key:t.id+"",note:t})})},n.loadNotes=function(){v.getNotes(function(t){return n.setState(function(){return{notes:t}})})},n.componentDidMount=function(){n.loadNotes()},n.state={notes:[]},n}return Object(m.a)(e,t),Object(l.a)(e,[{key:"render",value:function(){return o.a.createElement("div",null,o.a.createElement(i.b,{to:"/add"},"New"),this.getNoteItems())}}]),e}(o.a.Component),j=n(15),E=function t(){Object(u.a)(this,t),this.id=null,this.title="",this.text="",this.tags=[],this.user=null,this.createDate=new Date},O=function t(e){Object(u.a)(this,t),this.id=null,this.name="",this.name=e},w=function(t){function e(t){var n;return Object(u.a)(this,e),(n=Object(h.a)(this,Object(p.a)(e).call(this,t))).componentDidMount=function(){n.props.location.pathname.startsWith("/note/")&&v.getNoteById(n.props.match.params.id,function(t){return n.setState(t)})},n.changeTitle=function(t){n.setState({title:t.target.value})},n.changeText=function(t){n.setState({text:t.target.value})},n.changeTags=function(t){n.setState({tags:t.target.value.split(",").map(function(t){return new O(t.trim())})})},n.saveNote=function(){v.saveNote(n.state,function(){return n.props.history.push("/")})},n.render=function(){return o.a.createElement("div",null,o.a.createElement("input",{type:"text",placeholder:"\u0417\u0430\u0433\u043e\u043b\u043e\u0432\u043e\u043a",value:n.state.title,onChange:n.changeTitle}),o.a.createElement("textarea",{placeholder:"\u0422\u0435\u043a\u0441\u0442 \u0437\u0430\u043c\u0435\u0442\u043a\u0438",value:n.state.text,onChange:n.changeText}),o.a.createElement("input",{type:"text",placeholder:"\u0422\u0435\u0433\u0438",value:n.state.tags.map(function(t){return t.name}).join(", "),onChange:n.changeTags}),o.a.createElement("button",{onClick:n.saveNote},"\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c"),o.a.createElement(i.b,{to:"/"},"Back"),console.log(n.props))},n.state=new E,n.changeTitle=n.changeTitle.bind(Object(j.a)(n)),n}return Object(m.a)(e,t),e}(o.a.Component);r.a.render(o.a.createElement(i.a,null,o.a.createElement(s.a,{exact:!0,path:"/",component:b}),o.a.createElement(s.a,{exact:!0,path:"/add",component:w}),o.a.createElement(s.a,{path:"/note/:id",component:w})),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(t){t.unregister()})}},[[34,1,2]]]);
//# sourceMappingURL=main.3f51d571.chunk.js.map
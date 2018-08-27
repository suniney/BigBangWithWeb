# BigBangWithWeb
仿锤子大爆炸功能，注入Js实现网页分词爆炸

在每一个webview的文字位置，长按可实现bigbang文字炸出


js注入代码
```
"javascript: window.__dominoPlugin__= (function(){ var obj = { timeThrehold: 1000, distThrehold: 2, startX: 0, startY :0, x:0, y:0, animateId: null, startTime: (new Date()).getTime(), target: null }; var getClosestTextTag = function(elm) { var tag = 'p'; var matchesFn; ['matches','webkitMatchesSelector','mozMatchesSelector','msMatchesSelector','oMatchesSelector'].some(function(fn) { if (typeof document.body[fn] == 'function') { matchesFn = fn; return true; } return false; }); if (elm && elm[matchesFn] && elm[matchesFn](tag)) {return elm} var parent; while(elm) { parent = elm.parentElement; if (parent && parent[matchesFn] && parent[matchesFn](tag)) { return parent; } elm = parent; } return null; }; var outputToJava = function(){ var elm = getClosestTextTag(obj.target); var txt = elm && elm.innerText.trim(); if(txt) {window.__domino__.getContent(txt)} }; var onTouchMove = function(e){ obj.x = e.touches[0].clientX; obj.y = e.touches[0].clientY; }; var monitor = function(){ var dx = obj.x - obj.startX; var dy = obj.y - obj.startY; var dist = Math.sqrt(dx*dx + dy*dy); var elapse = (new Date()).getTime() - obj.startTime; if (dist < obj.distThrehold) { if(elapse < obj.timeThrehold) { obj.animateId = setTimeout(monitor, 0); } else { outputToJava(); clearTimeout(obj.animateId); } } else { clearTimeout(obj.animateId); } }; document.addEventListener('touchstart', function(e){ var evt = e.touches[0]; obj.startX = obj.x = evt.clientX; obj.startY = obj.y = evt.clientY; obj.startTime = (new Date()).getTime(); obj.target = evt.target; monitor(); document.addEventListener('touchmove', onTouchMove , false) }, false); document.addEventListener('touchend', function(e){ document.removeEventListener('touchmove', onTouchMove); clearTimeout(obj.animateId); }, false); })();";

```
![image](https://github.com/suniney/BigBangWithWeb/raw/master/image/bigbang.jpg)

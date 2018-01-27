var param;
var ctx;
var layout = new Vue({
    el: "#layout",
    data: {
        data:[{
            "icon":"el-icon-location",
            "label": "一级 1",
            "children": [{
              "label": "二级 1-1",
              "children": [{
                "label": "三级 1-1-1"
              }]
            }]
          }, {
            "icon":"el-icon-menu",
            "label": "一级 2",
            "children": [{
              "label": "二级 2-1",
              "children": [{
                "label": "三级 2-1-1"
              }]
            }, {
              "label": "二级 2-2",
              "children": [{
                "label": "三级 2-2-1"
              }]
            }]
          }, {
            "icon":"el-icon-setting",
            "label": "一级 3",
            "children": [{
              "label": "二级 3-1",
              "children": [{
                "label": "三级 3-1-1"
              }]
            }, {
              "label": "二级 3-2"
            }]
          }],
          popover2:false,
        isCollapse: false,
        defaultProps: {
            children: 'children',
            label: 'label'
            }
    },
    methods: {
        loaddata:function(){
    
        },
        handleNodeClick:function(){
        
        },
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        }
    }   
});

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
if($("body").width() < 1000){
  layout.isCollapse = true;
}else{
  layout.isCollapse = false;
}

$(function () {
    ctx = getContextPath();
    layout.loaddata();    
    var height =$("#layout").height() - 70; 
    console.log($("#layout").height(height));
    window.onresize = function(){
      if($("body").width() < 1000){
        layout.isCollapse = true;
      }else{
        layout.isCollapse = false;
      }
    }
});

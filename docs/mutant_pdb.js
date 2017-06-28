document.body.style.backgroundColor = "black";

document.addEventListener("DOMContentLoaded", function () {


     // ngl stage object
       var stage = new NGL.Stage("viewport");
         stage.loadFile("").then(function (component) {
         component.addRepresentation("cartoon");
         component.autoView();
         }
                                            );

       window.addEventListener( "resize", function( event ){
           stage.handleResize();
       }, false );


    function addElement (el) {
        Object.assign(el.style, {
            position: "absolute",
            zIndex: 10
        });
        stage.viewer.container.appendChild(el);
    }

       function createElement (name, properties, style) {
           var el = document.createElement(name);
           Object.assign(el, properties);
           Object.assign(el.style, style);
           return el;
       }

       function createSelect (options, properties, style) {
           var select = createElement("select", properties, style);
           options.forEach(function (d) {
               select.add(createElement("option", {
                   value: d[ 0 ], text: d[ 1 ]
               }));
           });
           return select;
       }

       function createFileButton (label, properties, style) {
           var input = createElement("input", Object.assign({
               type: "file"
           }, properties), { display: "none" });
           addElement(input);
           var button = createElement("input", {
               value: label,
               type: "button",
               onclick: function () { input.click(); }
           }, style);
           return button;
       }

       var struct1;

       function loadStructure (input) {
           // stage.removeAllComponents();
           struct1 = stage.loadFile(input).then(function (o) {
               struct1 = o;
               o.autoView();
               o.addRepresentation(polymerSelect.value, {
                   sele: "polymer",
                   name: "polymer"
               });
               o.addRepresentation("cartoon", { color: "tomato" });
               });
           };



       var struct2;

       function loadStructure2 (input) {
           // stage.removeAllComponents();
           struct2 = stage.loadFile(input).then(function (o) {
               struct2 = o;
               o.autoView();
               o.addRepresentation(polymerSelect.value, {
                   sele: "polymer",
                   name: "polymer"
               });
               o.addRepresentation("cartoon", { color: "yellow" });
               });
           };


       var loadStructureButton = createFileButton("load structure", {
           accept: ".pdb,.cif,.ent,.gz",
           onchange: function (e) {
               if (e.target.files[ 0 ]) {
                   loadStructure(e.target.files[ 0 ]);
               }
           }
       }, { top: "12px", left: "12px" });
       addElement(loadStructureButton);

       var loadStructureButton2 = createFileButton("load structure mutant", {
           accept: ".pdb,.cif,.ent,.gz",
           onchange: function (e) {
               if (e.target.files[ 0 ]) {
                   loadStructure2(e.target.files[ 0 ]);
               }
           }
       }, { top: "12px", left: "105px" });
       addElement(loadStructureButton2);

       var polymerSelect = createSelect([
           [ "cartoon", "cartoon" ],
           [ "spacefill", "spacefill" ],
           [ "licorice", "licorice" ],
           [ "surface", "surface" ]
       ], {
           onchange: function (e) {
               stage.getRepresentationsByName("polymer").dispose();
               stage.eachComponent(function (o) {
                   o.addRepresentation(e.target.value, {
                       sele: "polymer",
                       name: "polymer"
                   });
               });
           }
       }, { top: "36px", left: "12px" });


       var centerButton = createElement("input", {
           type: "button",
           value: "center",
           onclick: function () {
               stage.autoView(1000);
           }
       }, { top: "108px", left: "12px" });
       addElement(centerButton);


       var struct1chainlabel = createElement("span", {
           innerText: "struct 1 chain",
           title: "press enter to select residue chain"
       }, { top: "180px", left: "12px", color: "lightgrey" });

       addElement(struct1chainlabel);


       var chainStruct1;

       var struct1chain = createElement("input", {
           type: "text",
           title: "struct 1 chain",
           onkeypress: function (e) {
               var value = e.target.value;
               var character = String.fromCharCode(e.which);
               if (e.keyCode === 13) {
                   e.preventDefault();

                   var sele = value;
                   chainStruct1 = sele;
                   //struct1.addRepresentation("cartoon", {sele: sele, color: "blue"});

                   //// debug statement
                   //document.body.style.backgroundColor = "red";
                   //console.log(sele);

               } else {
                   console.log("something ");
               }
           }
       }, {top: "200px", left: "12px", color: "lightgrey" });

       addElement(struct1chain);

       var struct1reslabel = createElement("span", {
           innerText: "struct 1 res number",
           title: "press enter to select residue number"
       }, { top: "230px", left: "12px", color: "lightgrey" });

       addElement(struct1reslabel);


       var struct1res = createElement("input", {
           type: "text",
           title: "struct 1 res",
           onkeypress: function (e) {
               var value = e.target.value;
               var character = String.fromCharCode(e.which);
               if (e.keyCode === 13) {
                   e.preventDefault();

                   var sele =  value + ":" + chainStruct1;
                   struct1.addRepresentation("spacefill", {sele: sele, color: "green"});
                   console.log(sele);

               } else {
                   console.log("press the right button");
               }
           }
       }, { top: "250px", left: "12px", color: "lightgrey" });

       addElement(struct1res);

       var struct2chainlabel = createElement("span", {
           innerText: "struct 2 chain",
           title: "press enter to select residue chain"
       }, { top: "280px", left: "12px", color: "lightgrey" });

       addElement(struct2chainlabel);

       var chainStruct2;

       var struct2chain = createElement("input", {
           type: "text",
           title: "struct 2 chain",
           onkeypress: function (e) {
               var value = e.target.value;
               var character = String.fromCharCode(e.which);
               if (e.keyCode === 13) {
                   e.preventDefault();

                   var sele = value;
                   chainStruct2 = sele;
                 // struct2.addRepresentation("cartoon", {sele: sele, color: "pink"});

               } else {
                   console.log("press the right button");
               }
           }
       }, {top: "300px", left: "12px", color: "lightgrey" });

       addElement(struct2chain);

       var struct2reslabel = createElement("span", {
           innerText: "struct 2 res number",
           title: "press enter to select residue number"
       }, { top: "330px", left: "12px", color: "lightgrey" });

       addElement(struct2reslabel);

       var struct2res = createElement("input", {
           type: "text",
           title: "",
           onkeypress: function (e) {
               var value = e.target.value;
               var character = String.fromCharCode(e.which);
               if (e.keyCode === 13) {
                   e.preventDefault();

                   var sele =  value + ":" + chainStruct2;
                   struct2.addRepresentation("cartoon", {sele: sele, color: "green"});

               } else {
                   console.log("press the right button");
               }
           }
       }, { top: "350px", left: "12px", color: "lightgrey" });

       addElement(struct2res);


       var alignButton = createElement("input", {
           type: "button",
           value: "align",
           onclick: function () {
               var s1 = struct1.structure;
               var s2 = struct2.structure;
               console.log(s1, s2);
               NGL.superpose(s1, s2, true);
               struct1.updateRepresentations({ position: true });
               struct1.autoView();
           }
       }, { top: "140px", left: "12px" });

       addElement(alignButton);

       var clearButton = createElement("input", {
           type: "button",
           value: "clear",
           onclick: function () {
               stage.removeAllComponents();
           }
       }, { top: "500px", left: "12px" });

       addElement(clearButton);

   });

--- 
+++ 
@@ -18,7 +18,7 @@
             // first: let's check to see if this might be part of value with external type id:
             // 11-Sep-2015, tatu: Important; do NOT pass buffer as last arg, but null,
             //   since it is not the bean
-            if (ext.handlePropertyValue(p, ctxt, propName, buffer)) {
+            if (ext.handlePropertyValue(p, ctxt, propName, null)) {
                 ;
             } else {
                 // Last creator property to set?
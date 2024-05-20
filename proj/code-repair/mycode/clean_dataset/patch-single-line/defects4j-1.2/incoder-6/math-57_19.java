--- 
+++ 
@@ -12,7 +12,9 @@
     while (resultSet.size() < k) {
         // For each data point x, compute D(x), the distance between x and
         // the nearest center that has already been chosen.
-        int sum = 0;
+        // Choose the point x at random from the remaining data points.
+        // Note that this implementation is biased.
+        double sum = 0.;
         for (int i = 0; i < pointSet.size(); i++) {
             final T p = pointSet.get(i);
             final Cluster<T> nearest = getNearestCluster(resultSet, p);
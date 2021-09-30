# Libraries
library(ggplot2)
library(igraph)

pdf("circle.pdf")

# create data
xValue <- 1:10
yValue <- cumsum(rnorm(10))


{
  #selected blocks
  library(ggplot2)
  blocks = scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/longestTime")
  xValue = 1:blocks[1]
  plot(1, type="n", xlab="ms", ylab="#nodes", xlim=c(0, blocks[1]), ylim=c(1, blocks[3]))
  custom <- 13:19
  custom <- c(23,24,28)
  #custom <- c(23)
  custom <-  2:(blocks[2] -3)
  debug = vector()
  for (block in custom){
    yValue = scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/r_graph", skip = block-1, nlines = 1)
    debug <- c(debug, list(yValue))
    if(length(yValue) == 0) {
      break
    }
    lines(1:length(yValue) , yValue)
  }
}

{
  #AVG
  yValue <- vector()
  testArray <- array(dim=c(length(custom),blocks[1]))
  for (i in 1:length(custom)){
    scaned <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/r_graph", skip = custom[i]-1, nlines = 1)
    debugInt <- blocks[1] - length(scaned)
    testArray[i,] <- matrix(c(scaned, rep(blocks[3] , blocks[1] - length(scaned) + 1)) , nrow = 1, ncol = blocks[1])
  }
  mean <- colMeans(testArray)
  lines(1:length(mean) , mean, col = "red", lwd=2)
}


#layout_in_circle layout_as_tree  layout_nicely  layout_with_graphopt
#add_layout_(), component_wise(), layout_as_bipartite(), layout_as_star(), layout_as_tree(), 
#layout_in_circle(), layout_nicely(), layout_on_grid(), layout_on_sphere(), layout_randomly(), 
#layout_with_dh(), layout_with_fr(), layout_with_gem(), layout_with_graphopt(), layout_with_kk(), 
#layout_with_lgl(), layout_with_mds(), layout_with_sugiyama(), merge_coords(), norm_coords(), normalize()

{
  # NETWORK
  library(igraph)
  from <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/from")
  to <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/to")
  nodes <- 0:max(c(max(from), max(to)))
  edges <- list(from, to)
  routes_igraph <- graph_from_data_frame(d = edges, vertices = nodes, directed = FALSE)
  V(routes_igraph)$role <- c("validator" , rep("node", max(nodes)))
  plot(routes_igraph, layout = layout_with_graphopt, edge.arrow.size = 0, vertex.color=c("pink", "skyblue")[1+(V(routes_igraph)$role=="node")])
  #plot(routes_igraph, edge.arrow.size = 0)
}

dev.off()

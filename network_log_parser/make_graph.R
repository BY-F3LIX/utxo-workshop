# Libraries
library(ggplot2)
library(igraph)

pdf("withOut_Delay.pdf")

# create data
xValue <- 1:10
yValue <- cumsum(rnorm(10))


{
  #selected blocks
  library(ggplot2)
  blocks = scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/longestTime")
  xValue = 1:blocks[1]
  blocks[1] <- 4200
  plot(1, type="n", xlab="ms", ylab="#nodes", xlim=c(0, blocks[1]), ylim=c(1, blocks[3]))
  #plot(1, type="n", xlab="ms", ylab="#nodes", xlim=c(0, 300), ylim=c(1, blocks[3]))
  custom <- 13:19
  custom <- c(23,24,28)
  #custom <- c(23)
  custom <-  0:(blocks[2])
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
  for (i in 1:(length(custom) - 1)){
    scaned <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/r_graph", skip = custom[i]-1, nlines = 1)
    debugInt <- blocks[1] - length(scaned)
    print(scaned[length(scaned)])
    lastInt = scaned[length(scaned)]
    if(is.na(scaned[length(scaned)])){
      break
    }
    if(scaned[length(scaned)] < blocks[3])  {
      lastInt = NA
    } 
    testArray[i,] <- matrix(c(scaned, rep(lastInt , blocks[1] - length(scaned) + 1)) , nrow = 1, ncol = blocks[1])
  }
  mean <- colMeans(testArray, na.rm = TRUE)
  lines(1:length(mean) , mean, col = "red", lwd=2)
}


#layout_in_circle layout_as_tree  layout_nicely  layout_with_graphopt
#add_layout_(), component_wise(), layout_as_bipartite(), layout_as_star(), layout_as_tree(), 
#layout_in_circle(), layout_nicely(), layout_on_grid(), layout_on_sphere(), layout_randomly(), 
#layout_with_dh(), layout_with_fr(), layout_with_gem(), layout_with_graphopt(), layout_with_kk(), 
#layout_with_lgl(), layout_with_mds(), layout_with_sugiyama(), merge_coords(), norm_coords(), normalize()

SEED <- 42

{
  # NETWORK
  library(igraph)
  set.seed(SEED)
  from <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/from")
  to <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/to")
  nodes <- 0:max(c(max(from), max(to)))
  edges <- list(from, to)
  routes_igraph <- graph_from_data_frame(d = edges, vertices = nodes, directed = FALSE)
  V(routes_igraph)$role <- c("validator" , rep("node", max(nodes)))
  plot(routes_igraph, layout = layout_with_graphopt, edge.arrow.size = 0, vertex.color=c("pink", "skyblue")[1+(V(routes_igraph)$role=="node")])
  #plot(routes_igraph, edge.arrow.size = 0)
}




{
  # NETWORK GIF
  library(igraph)
  makeGif<-FALSE
  from <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/from")
  to <- scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/to")
  nodes <- 0:max(c(max(from), max(to)))
  edges <- list(from, to)
  routes_igraph <- graph_from_data_frame(d = edges, vertices = nodes, directed = FALSE)
  
  fileName="/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/gifFile"
  lines=length(readLines(file(fileName,open="r")))
  for ( i in 0:lines-1){
    if(makeGif){
      png(file=paste("/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/gifPNGs/png_" , i , sep=""),
        width=600, height=350)
    }
    V(routes_igraph)$role <- scan(fileName, skip = i, nlines = 1)
    set.seed(SEED)
    plot(routes_igraph, layout = layout_with_graphopt, edge.arrow.size = 0, vertex.color=c("pink", "skyblue")[1+(V(routes_igraph)$role==0)])
    if(makeGif){
      dev.off()
    }
  }
  
  
  
  #plot(routes_igraph, edge.arrow.size = 0)
}

dev.off()

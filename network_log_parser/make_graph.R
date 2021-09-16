# Libraries
library(ggplot2)
library(igraph)

# create data
xValue <- 1:10
yValue <- cumsum(rnorm(10))

{
  yValue = scan("/home/felix/Documents/programming/felix_utxo/utxo-workshop/network_log_parser/r_graph")
  xValue = 1:length(yValue)
  data <- data.frame(xValue,yValue)
  # Plot
  ggplot(data, aes(x=xValue, y=yValue)) + 
    geom_line()
}

{
  nodes <- 1:10
  from <- round(runif(10,1,10))
  to <- round(runif(10,1,10))
  edges <- list(from, to)
  routes_igraph <- graph_from_data_frame(d = edges, vertices = nodes, directed = TRUE)
  plot(routes_igraph, layout = layout_with_graphopt, edge.arrow.size = 0)
  
}

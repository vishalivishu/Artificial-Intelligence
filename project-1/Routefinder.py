#/usr/bin/python
from itertools import islice

my_cost = {}
depth_state = {}
heuristic = {}

def find_parent(parent,start,end):
	item = end
	short_path = [end]
	while item != start :
		short_path.append(parent[item])
		item = parent[item]
	short_path.reverse()
	return short_path


def bfs(my_graph,start,end):
	# print "inside bfs",start,end
	# for x,y in my_graph.items():
		# print x," ",y
	visited_set = set()
	path_que = []
	parent_state = {}
	path_que.append(start)
	while path_que:
		state = path_que.pop(0)
		# print "state is",state,end
		if state.strip() == end:
			# print "reached end",end
			return find_parent(parent_state, start, end)
		elif state not in visited_set and state in my_graph.keys():
			# print "elif",state
			for next_state in my_graph[state.strip()]:
				# print "next state of ",state," is ",next_state
				if(next_state not in parent_state):
					parent_state[next_state] = state 
					# print parent_state[next_state]
				path_que.append(next_state)
				visited_set.add(state)

def dfs(my_graph,start,end):
	# print "inside dfs",start,end
	# for x,y in my_graph.items():
		# print x," : ",y
	path_que = []
	parent_state = {}
	depth_state = {}
	visited_set = set()
	path_que.append(start)
	depth_state[start] = 0;
	while path_que:
		state = path_que.pop()
		# print "state is",state,end
		if state.strip() == end:
			# print "reached end",end
			return find_parent(parent_state,start,end)
		elif state not in visited_set and state in my_graph.keys():
			# print "elif",state
			que = []
			for next_state in my_graph[state.strip()]:
				# print "next state of ",state," is ",next_state
				if next_state not in depth_state.keys():
					depth_state[next_state] = depth_state[state]+1
					parent_state[next_state] = state
					que.append(next_state)
					
				elif depth_state[next_state] > depth_state[state]+1 :
					depth_state[next_state] = depth_state[state]+1
					parent_state[next_state] = state
				
				# print "parent of",next_state,parent_state[next_state]
				# print depth_state[next_state]
				
				visited_set.add(state)
			que.reverse()
			for i  in que:
				path_que.append(i)
			# for i in path_que:
				# print "+++",i
				
def ucs(my_graph,start,end):
	
	# print "inside ucs",start,end
	# for x,y in my_graph.items():
		# print x," : ",y
	parent_state = {}
	priority_list = []
	depth_state[start] = 0;
	q='0'
	priority_list.append((start,depth_state[start]))
	while priority_list:
		state_tuple = priority_list.pop(0)
		state = state_tuple[0]
		# print "state is",state,end
		if state.strip() == end:
			# print "reached end",end
			return	find_parent(parent_state, start, end)
		elif state in my_graph.keys():
			# print "elif",state
			for next_state in my_graph[state.strip()]:
				# print "next state of ",state," is ",next_state
				if next_state not in depth_state.keys():
					depth_state[next_state] = my_cost[state,next_state]+depth_state[state];
					parent_state[next_state] = state
					priority_list.append((next_state,depth_state[next_state]))
					
				elif depth_state[next_state] > (my_cost[state,next_state]+depth_state[state]):
					if any(q==next_state for (next_state,_) in priority_list):
						priority_list.remove((next_state,depth_state[next_state]))
					depth_state[next_state] = my_cost[state,next_state]+depth_state[state]
					parent_state[next_state] = state
					priority_list.append((next_state,depth_state[next_state]))
				
				# print parent_state[next_state]
				# print depth_state[next_state]
				
				priority_list.sort(key = lambda x:x[1])

				
def Astar(my_graph,start,end):
	# print "A*", start, end
	# for x,y in my_graph.items():
		# print x," : ",y
	parent_state = {}
	priority_list = []
	h_cost = {}
	visited_set=set()
	depth_state[start] = 0;
	h_cost[start]=heuristic[start]+depth_state[start]
	q='0'
	priority_list.append((start,depth_state[start],h_cost[start]))
	while priority_list:
		state_tuple = priority_list.pop(0)
		state = state_tuple[0]
		visited_set.add(state)
		# print "state is",state,end
		if state.strip() == end:
			# print "reached end",end
			return	find_parent(parent_state, start, end)
		elif state in my_graph.keys():
			# print "elif",state
			for next_state in my_graph[state.strip()]:
				# print "next state of ",state," is ",next_state
				if next_state not in depth_state.keys():
					depth_state[next_state] = my_cost[state,next_state]+depth_state[state];
					h_cost[next_state] = my_cost[state,next_state]+depth_state[state]+heuristic[next_state]
					parent_state[next_state] = state
					priority_list.append((next_state,depth_state[next_state],h_cost[next_state]))
					
				elif h_cost[next_state] > (my_cost[state,next_state]+depth_state[state]+heuristic[next_state]):
					if any(q==next_state for (next_state,_,_) in priority_list):
						priority_list.remove((next_state,depth_state[next_state],h_cost[next_state]))
					depth_state[next_state] = my_cost[state,next_state]+depth_state[state]
					h_cost[next_state] = my_cost[state,next_state]+depth_state[state]+heuristic[next_state]
					parent_state[next_state] = state
					priority_list.append((next_state,depth_state[next_state],h_cost[next_state]))
				
				# print parent_state[next_state],depth_state[next_state]
				
				priority_list.sort(key = lambda x:x[2])
	# for x in depth_state:
		# print "***",x,depth_state[x],"***"
	
	
def write_op_tofile(path):
	cost = 0
	f = open("output.txt","w")
	for item in path:
		f.write(item)
		f.write(" ")
		f.write(str(cost))
		f.write("\n")
		cost+=1
	f.close()	

def write_op_tofileUCS(path):
	f = open("output.txt","w")
	# print "in ucs print"
	for item in path:
		f.write(item)
		f.write(" ")
		f.write(str(depth_state[item]))
		f.write("\n")
	f.close()	
	
def get_input():
	
	my_graph = {}
	fp = open("input.txt","r")
	algo = fp.readline().strip()
	start_state = fp.readline().strip()
	goal_state = fp.readline().strip()
	num_traffic = int(fp.readline())
	# print num_traffic
	live_traffic_line = list(islice(fp,num_traffic))
	# print "traffic list------"
	for x in live_traffic_line:
		# print x
		word = x.split()
		if word[0] in my_graph:
			my_graph[word[0]] = my_graph.get(word[0])+ [word[1]]
		else:
			my_graph[word[0]] = [word[1]]
		
		my_cost[(word[0],word[1])] = int(word[2].strip())
	# for x,y in my_graph.items():
		# print x," ",y
	
	num1_traffic = int(fp.next())
	# print "sunday trafic count is ",num1_traffic
	sun_traffic_line = list(islice(fp,num1_traffic))
	for x in sun_traffic_line:
		y=x.split()
		# print y[0]
		heuristic[y[0].strip()]=int(y[1].strip())
	fp.close()
	
	my_algo = {
"BFS" : bfs,
"DFS" : dfs,
"UCS" : ucs,
"A*"  : Astar
}
	# print "+++++++++++++++++++++++++++",algo
	path = my_algo[algo](my_graph,start_state,goal_state)
	# print path
	# print "-----------------",algo=='UCS' or algo=='A*',algo
	if algo=='UCS' or algo=='A*':
		write_op_tofileUCS(path)
	else:
		# print "inside else"
		write_op_tofile(path)
	

get_input()

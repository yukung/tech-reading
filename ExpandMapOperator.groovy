assert [a:100, *:[e:4, f:5]] == [a:100,e:4,f:5]
m = [b:1,c:2,d:3]
assert [a:100, *:m] == [a:100,b:1,c:2,d:3]

a=int(input()) 
for i in range(a):
    for j in range(a,0,-1):
        if(j>i+1):
            print(' ', end ='' )
        else:
            print('*', end ='')
    print()
       
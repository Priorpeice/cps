#include <stdio.h>
int main(void)
    
{
    int x,y,i;
    
    scanf("%d", &i);
    
    
    for(y=1;y<=i;y++)
    {
        for(x=0;x<y;x++)
        {
            printf("*");
        }
        printf("\n");
        
    }
    return 0;
    
}
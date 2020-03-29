public class testExample {
    public static void main(String[] args)
    {
        int []a={19,8,12,90};
        sortIncrease(a);
        display(a);
        System.out.print("\n");
        System.out.println("min is: "+min(a));

    }
    public static void sortIncrease(int[] a)
    {
        int temp =0;
        for(int i=0;i<a.length-1;i++)
        {
            for(int j=i+1;j<a.length;j++)
            {
                if(a[i]>a[j])
                {
                    temp=a[j];
                    a[j] = a[i];
                    a[i]=temp;
                }
            }
        }
    }
    public static void display(int[]a)
    {
        for(int i=0;i<a.length;i++)
        {
            System.out.print(a[i] +"\t");
        }
    }
    public static int min(int []a)
    {
        int min=a[0];
        for(int i=0;i<a.length;i++)
        {
            if(a[i]<min)
                min=a[i];
        }
        return min;
    }
}

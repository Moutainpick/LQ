//结对开发—二 胡金辉 李青 

#include<stdio.h>
int _tmain(int argc, _TCHAR* argv[])
{
	int i, j, k, m, n, o, a[5];
	int Sum, Max, flag, flag1 = 0, flag2, flag4;

	printf("请输入5个整数：\n");
	for (k = 0; k<5; k++)
	{
		scanf("%d", &a[k]);
	}
	Max = a[0];
	for (m = 0; m<5; m++)
	{
		for (i = 0; i<5; i++)
		{
			Sum = 0;
			for (j = i; j<5; j++)
			{
				Sum = Sum + a[j];
				if (Sum <= 0)
				{
					Sum = 0;
					flag1 = (j + 1 + m) % 5;
				}
				if (Sum > Max)
				{
					Max = Sum;
					flag2 = j + m;
				}
			}
		}
		flag = a[0];
		for (n = 0; n<5; n++)
		{
			a[n] = a[n + 1];
		}
		a[4] = flag;
	}
	if (Sum == 0)
	{

		Max = a[0];
		for (int e = 0; e<5; e++)
		{
			if (a[e] >= Max)
			{
				Max = a[e];
				flag4 = e;
			}
		}

	}
	printf("最大连续环子数组的和为：%d\n", Max);
	printf("该最大连续环子数组为：");

	if (Sum == 0)
	{
		printf("a[%d]", flag4);
	}
	else
	{
		int flag3 = flag2 - flag1;
		for (o = 0; o <= flag3; o++)
		{
			printf("a[%d]\t", flag1);
			flag1++;
			if (flag1>4)
				flag1 = 0;
		}
	}
	printf("\n");
	return 0;
}
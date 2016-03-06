//实现随机生成30道小学四则运算题的程序，李青，2016/3/5
#include<stdio.h>
#include<iostream>
#include<time.h>
using namespace std;
void main()
{
	srand((int)time(NULL));//时间种子
	int i, j, k, numerator1, denominator1,numerator2,denominator2,exchange1,exchange2;
	char operation;
	for (i = 0; i < 30; i++)//循环输出
	{
		j = rand() % 100;
		k = rand() % 100;
		numerator1 = rand() % 100;//分子1
		denominator1 = rand() % 100;//分母1
		numerator2 = rand() % 100;//分子2
		denominator2 = rand() % 100;//分母2
		//以下两个是对真分数分母的非零判断及更正
		while (denominator1 == 0)
		{
			denominator1 = rand() % 100;
			break;
		}
		while (denominator2 == 0)
		{
			denominator2 = rand() % 100;
			break;
		}
		if (i % 2 == 0)//判断是整数运算
		{
			if (j % 4 == 0) operation = '+';
			else if (j % 4 == 1) operation = '-';
			else if (j % 4 == 2) operation = '*';
			else//如果是除法运算，对分母进行非零判断
			{
				while (numerator2 == 0)
				{
					numerator2 = rand() % 100;
					break;
				}
				operation = '/';
			}
			cout << numerator1 << "  " << operation << "  " << numerator2 << "   =" << endl;
		}
		else//是真分数运算
		//对分数的判断，将不是真分数的转换成真分数
		while (numerator1 > denominator1)
		{
			exchange1 = numerator1;
			numerator1 = denominator1;
			denominator1 = exchange1;
		}
		while (numerator2 > denominator2)
		{
			exchange2 = numerator2;
			numerator2 = denominator2;
			denominator2 = exchange2;
		}
		if (j % 4 == 0) operation = '+';
		else if (j % 4 == 1) operation = '-';
		else if (j % 4 == 2) operation = '*';
		else//如果是除法运算，对分母进行非零判断
		{
			while (numerator2 == 0)
			{
				numerator2 = rand() % 100;
				break;
			}
			operation = '/';
		}
		cout << "(" << numerator1 << "/" << denominator1 << ")" <<"  "<< operation << "  " << "(" << numerator2 << "/" << denominator2 << ")" << "   =" << endl;
	}
}
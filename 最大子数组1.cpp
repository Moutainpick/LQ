//计算一列数组中最大子数组之和，李青，胡金辉
#include<iostream>
using namespace std;
#define max(x,y)  ( x>y?x:y )
int main()
{
	int i,length=0,list[1000];//定义循环变量、数组长度、数组
	int sum1;//记录包含当前数组元素的最大子数组之和
	int sum2;//记录不包含当前数组元素的最大子数组之和
	cout << "请输入数组元素：" << endl;
		while ((cin >> list[length++] ) && getchar() != '\n');
		cout << "你输入了" << length << "个数" << endl;
	
	sum1 = list[0];
	sum2 = 0;
	for (i = 1; i < length; i++)
	{
		sum2 = max(sum2, sum1);
		sum1 = max(sum1 + list[i], list[i] );
	}
	sum1 = max(sum2, sum1);
	cout << "你输入的数组中最大子数组的值为："<<sum1<<endl;
	return 0;
}
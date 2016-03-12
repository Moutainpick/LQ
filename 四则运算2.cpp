//随机生成四则运算题目，李青，2016/3/11
#include<iostream>
#include<stdlib.h>
#include<time.h>
using namespace std;
void main()
{
	srand((int)time(NULL));//时间种子
	int amount, mul_div, brackets, range, negative_number, remainder,i;
	int number1, number2,exchange1;
	cout << "请输入需要生成的运算题目数量：" << endl;
	cin >> amount;
	cout << "请输入生成的题目中数值范围（不大于该数）：" << endl;
	cin >> range;
	cout << "生成的题目中是否有括号？1：有，2：没有   (没有实现该功能) " << endl;
	cin >> brackets;
	if (brackets == 2)
	{
		cout << "生成的题目中是否有乘除法？1：有， 2：没有" << endl;
		cin >> mul_div;
		while (mul_div == 1)
		{
			cout << "除法是整除吗？1：是，2：不是" << endl;
			cin >> remainder;
			break;
		}
		cout << "减法有负数吗？1：有，2：没有" << endl;
		cin >> negative_number;
		if (negative_number == 2)//无负数
		{
			if (mul_div == 1)//有乘除法
			{
				if (remainder == 1)//无余数
				{
					for (i = 0; i < amount; i++)
					{
						char operation[4] = { '+', '-', '*', '/' };//对运算符号的定义
						int rand_op_index = rand() % 4;
						char current_op = operation[rand_op_index];
						number1 = rand() % range;
						number2 = rand() % range;
						if (current_op == '/')
						{
							while (number1 < number2)//确保除数比被除数小
							{
								exchange1 = number1;
								number1 = number2;
								number2 = exchange1;
							}
							while (number2 == 0 || number1 % number2 != 0)//确保是整除且除数不为0
							{
								number2 = rand() % (number1 - 1) + 1;
							}
							cout << number1 << current_op << number2 << "=" << endl;
						}
						else if (current_op == '-')
						{
							while (number1 < number2)//确保不会算出负数
							{
								exchange1 = number1;
								number1 = number2;
								number2 = exchange1;
							}
							cout << number1 << current_op << number2 << "=" << endl;
						}
						else
						{
							cout << number1 << current_op << number2 << "=" << endl;
						}
					}
				}
				else//有余数
				{
					for (i = 0; i < amount; i++)
					{
						char operation[4] = { '+', '-', '*', '/' };//对运算符号的定义
						int rand_op_index = rand() % 4;
						char current_op = operation[rand_op_index];
						number1 = rand() % range;
						number2 = rand() % range;
						if (current_op == '/')
						{
							while (number2 == 0)//确保除数不为0
							{
								number2 = rand() % (number1 - 1) + 1;
							}
							cout << number1 << current_op << number2 << "=" << endl;
						}
						else if (current_op == '-')
						{
							while (number1 < number2)//确保不会算出负数
							{
								exchange1 = number1;
								number1 = number2;
								number2 = exchange1;
							}
							cout << number1 << current_op << number2 << "=" << endl;
						}
						else
						{
							cout << number1 << current_op << number2 << "=" << endl;
						}
					}
				}
			}
			else//无乘除法
			{
				for (i = 0; i < amount; i++)
				{
					char operation[4] = { '+', '-' };//对运算符号的定义
					int rand_op_index = rand() % 2;
					char current_op = operation[rand_op_index];
					number1 = rand() % range;
					number2 = rand() % range;
					if (current_op == '-')
					{
						while (number1 < number2)//确保不会算出负数
						{
							exchange1 = number1;
							number1 = number2;
							number2 = exchange1;
						}
						cout << number1 << current_op << number2 << "=" << endl;
					}
					else
					{
						cout << number1 << current_op << number2 << "=" << endl;
					}
				}
			}
		}
		else//有负数
		if (mul_div == 1)//有乘除法
		{
			if (remainder == 1)//无余数
			{
				for (i = 0; i < amount; i++)
				{
					char operation[4] = { '+', '-', '*', '/' };//对运算符号的定义
					int rand_op_index = rand() % 4;
					char current_op = operation[rand_op_index];
					number1 = rand() % range;
					number2 = rand() % range;
					if (current_op == '/')
					{
						while (number1 < number2)//确保除数比被除数小
						{
							exchange1 = number1;
							number1 = number2;
							number2 = exchange1;
						}
						while (number2 == 0 || number1 % number2 != 0)//确保是整除且除数不为0
						{
							number2 = rand() % (number1 - 1) + 1;
						}
						cout << number1 << current_op << number2 << "=" << endl;
					}
					else
					{
						cout << number1 << current_op << number2 << "=" << endl;
					}
				}
			}
			else//有余数
			{
				for (i = 0; i < amount; i++)
				{
					char operation[4] = { '+', '-', '*', '/' };//对运算符号的定义
					int rand_op_index = rand() % 4;
					char current_op = operation[rand_op_index];
					number1 = rand() % range;
					number2 = rand() % range;
					if (current_op == '/')
					{
						while (number2 == 0)//确保除数不为0
						{
							number2 = rand() % (number1 - 1) + 1;
						}
						cout << number1 << current_op << number2 << "=" << endl;
					}
					else
					{
						cout << number1 << current_op << number2 << "=" << endl;
					}
				}
			}
		}
		else//无乘除法
		{
			for (i = 0; i < amount; i++)
			{
				char operation[4] = { '+', '-' };//对运算符号的定义
				int rand_op_index = rand() % 2;
				char current_op = operation[rand_op_index];
				number1 = rand() % range;
				number2 = rand() % range;
				cout << number1 << current_op << number2 << "=" << endl;
			}
		}
	}
	else//有括号的运算题目的生成，未实现
	{

	}
}
#include <bits/stdc++.h>

using namespace std;

bool student2BetterStudent(vector<vector<int>> &student_wish_list, vector<vector<int>> &school_preference, int school, int student, int student2)
{
  int n = student_wish_list.size();
  for (int i = 0; i < n; i++)
  {
    if (school_preference[school][i] == student2)
    {
      return true;
    }
    if (school_preference[school][i] == student)
    {
      return false;
    }
  }
}

int main()
{

  //freopen("in.txt","r",stdin);
  ios_base::sync_with_stdio(false);
  cin.tie(NULL);
  int t;
  cin >> t;
  while (t--)
  {
    int n;
    cin >> n;
    vector<vector<int>> student_wish_list(n);
    vector<vector<int>> school_preference(n);
    for (int i = 0; i < n; i++)
    {
      int ith_student;
      cin >> ith_student;
      for (int j = 0; j < n; j++)
      {
        int wished_school;
        cin >> wished_school;
        student_wish_list[ith_student - 1].push_back(wished_school - 1);
      }
    }

    for (int i = 0; i < n; i++)
    {
      int ith_school;
      cin >> ith_school;
      for (int j = 0; j < n; j++)
      {
        int pref_student;
        cin >> pref_student;
        school_preference[ith_school - 1].push_back(pref_student - 1);
      }
    }

    bool free_student[n];
    memset(free_student, false, sizeof(free_student));
    int match[n];
    memset(match, -1, sizeof(match));
    int cnt_free = n;
    while (cnt_free > 0)
    {
      int student;
      for (int i = 0; i < n; i++)
      {
        if (free_student[i] == false)
        {
          student = i;
          break;
        }
      }
      for (int i = 0; i < n && free_student[student] == false; i++)
      {
        int school = student_wish_list[student][i];
        if (match[school] == -1)
        {
          match[school] = student;
          free_student[student] = true;
          cnt_free--;
        }
        else
        {
          int student2 = match[school];
          if (student2BetterStudent(student_wish_list, school_preference, school, student, student2) == false)
          {
            match[school] = student;
            free_student[student] = true;
            free_student[student2] = false;
          }
        }
      }
    }

    for (int i = 0; i < n; i++)
    {
      cout << i + 1 << " " << match[i] + 1 << endl;
    }
  }

  return 0;
}

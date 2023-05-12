# پروژه‌ی برنامه‌سازی پیشرفته
شماره تیم: 11

- نام عضو اول: صادق سرگران

شماره دانشجویی عضو اول: 401106039

- نام عضو دوم: سپهر نوری

شماره دانشجویی عضو دوم: 401106652

- نام عضو سوم: امیررضا سعیدی

شماره دانشجویی عضو سوم: 401106041



لینک مخزن کامیت های اولیه (فاز صفر):
https://github.com/Sepehr-Noori/src-Fixed.git

_____
Commands


*SignupMenu*

- user create -u [username] -p [password] [password confirmation] -e [email] -n [nickname] -s [slogan] --> ساخت کاربر

- question pick -q [question number] -a [answer] -c [answer confirmation] --> انتخاب سوال امنیتی

*LoginMenu*

- user login -u [username] -p [password] (--stay-logged-in)? --> لاگین

- forgot my password -u [username] --> فراموشی رمز عبور

- user logout --> لاگ اوت

*MainMenu*

- enter map edit menu --> ویرایش نقشه

- enter profile menu --> منوی پروفایل

- start game -m [map name] -g [guests]... --> شروع بازی و ورود به منوی بازی

*ProfileMenu*

- profile change -[tag] [field] --> تغییر یک فیلد پروفایل

- profile change password -o [old password] -n [enw password] --> تغییر رمز عبور

- profile remove slogan --> حذف شعار

- profile display [field] --> نمایش یک فیلد پروفایل

*MapEditMenu*

- new map --> ساخت نقشه جدید

- save --> ذخیره نقشه

- show map --> نمایش نقشه

- set texture -x [x] -y [y] -t [type] --> مشخص کردن بافت نقشه در مختصات داده شده

- set texture -x1 [x1] -y1 [y1] -x2 [x2] -y2 [y2] -t [type] --> مشخص کردن بافت نقشه در ناحیه داده شده

- clear -x [x] -y [y] --> پاکسازی مختصات داده شده

- drop cliff -x [x] -y [y] --> قرار دادن صخره

- drop tree -x [x] -y [y] --> قرار دادن درخت

*GameMenu*

- next turn --> رفتن به نوبت بعد

- show map -x [x] -y [y] --> نمایش نقشه حول مختصات داده شده و ورود به منوی نقشه

- drop building -x -[x] -y [y] -type [type] --> قرار دادن ساختمان در مختصات داده شده

- select building -x [x] -y [y] --> انتخاب ساختمان در مختصات داده شده و ورود به منوی ساختمان

- show popularity (factors)? --> نمایش میزان محبوبیت یا معیار های محبوبیت

- show food list --> نمایش لیست غذا ها و مقدار

- food rate show --> نمایش نرخ مصرف غذا

- tax rate -r [rate] --> تغییر نرخ مالیات

- tax rate show --> نمایش نرخ مالیات

- fear rate -r [rate] --> تغییر شاخص ترس

- fear rate show --> نمایش شاخص ترس

- select unit -x [x] -y [y] -t [type] --> انتخاب یگان و ورود به منوی یگان

- drop unit -x [x] -y [y] -t [type] -c [count] --> قرار دادن یگان در مختصات داده شده (برای تست)

- show resource -r [resource] --> نمایش میزان منابع داده شده

- show details -x [x] -y [y] --> نمایش جزییات مختصات داده شده در نقشه

*ShowMapMenu*

- map [direction] [count] --> حرکت دادن نقشه

*SelectBuildingMenu*

- create unit -t [type] -c [count] --> ایجاد یگان در نقشه (اصلی)

- repair --> بازسازی ساختمان

*SelectUnitMenu*

- move unit to -x [x] -y [y] --> حرکت دادن یگان

- patrol unit -x1 [x1] -y1 [y1] --> گشت در مختصات داده شده

- stop patrol --> توقف گشت

- set -s [state] --> مشخص کردن حالت یگان

- attack -x [x] -y [y] --> حمله هوایی به مختصات داده شده

- attack -e [x] [y] --> حمله زمینی به مختصات داده شده

- dig tunnel -d [direction] --> حفر تونل در جهت داده شده

- build -q [machine type] --> ساخت تجهیزات محاصره

- disband unit --> تبدیل یگان ها به جمعیت

- deselect --> لغو انتخاب یگان

- pour oil -d [direction] --> ریختن نفت در جهت داده شده

*TradeMenu*

- trade -t [type] -a [amount] -p [price] -m [message] --> ایجاد ترید

- trade list --> نمایش کل ترید

- trade accept -i [id] -m [message] --> قبول یک ترید

- trade history --> نمایش تاریخچه ترید های حکومت

*MarketMenu*

- show price list --> نمایش لیست کالاها

- buy -i [item name] -a [amount] --> خرید آیتم

- sell -i [item name] -a [amount] --> فروش آیتم








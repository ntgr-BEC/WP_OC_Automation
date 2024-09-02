
import os, sys, time, xlrd, argparse
from xlutils.copy import copy

filePath = "C:\\Automation\\CONFIG\\insight_configure.xls"
sheet_name = "GlobalVariable"
parser = argparse.ArgumentParser(description='A tool to update insight config')
parser.add_argument('-t', default='a', help="build type, andriod(a), iOs(i), webportal(w)")
parser.add_argument('-c', default=filePath, help="config file")
parser.add_argument('-n', default='daily', help="dut name")
parser.add_argument('-d', required=True, help="folder to app file")
parser.add_argument('-f', required=True, help="app file name")
parser.add_argument('-v', required=True, help="app version")
args = parser.parse_args()

os.system('taskkill /f /im excel.exe')
time.sleep(2)

print 'open xls to update'
data = xlrd.open_workbook(args.c)
table = data.sheet_by_name(sheet_name)
is_android = False
pos_dir = 0
pos_fn = 0
pos_ver = 0
pos_dut_name = 0
for i in range(table.nrows):
    if table.cell(i, 0).value == "PUB_DUT_MODEL":
        pos_dut_name = i
        continue
    if table.cell(i, 0).value == "PUB_BINARY_PATH":
        pos_dir = i
        continue
    if table.cell(i, 0).value == "PUB_PLATFORM_Type":
        if table.cell(i, 1).value == '0':
            is_android = True
        continue
    if is_android and table.cell(i, 0).value == 'PUB_BINARY_FileName_A':
        pos_fn = i
        continue
    if not is_android and table.cell(i, 0).value == 'PUB_BINARY_FileName_I':
        pos_fn = i
        continue
    if is_android and table.cell(i, 0).value == 'PUB_BINARY_VER_A':
        pos_ver = i
        continue
    if not is_android and table.cell(i, 0).value == 'PUB_BINARY_VER_I':
        pos_ver = i
        continue

assert(pos_dir>0)
assert(pos_fn>0)
assert(pos_ver>0)
assert(pos_dut_name>0)
newdata = copy(data)
table = newdata.get_sheet(sheet_name)
table.write(pos_dir, 1, args.d)
table.write(pos_fn, 1, args.f)
table.write(pos_ver, 1, args.v)
table.write(pos_dut_name, 1, args.n)
newdata.save(args.c)

import os
import re


def is_test_files(directory):
    test_dirs = ('test', 'androidTest')
    for d in test_dirs:
        if d in directory:
            return True
    return False


def generate_tests():
    current_root = './'
    for current_dir, dirs, files in os.walk(current_root):
        for file in files:
            current_path = os.path.join(current_dir, file)
            if file.endswith('.kt') and 'src' in current_path:
                if is_test_files(current_path):
                    continue
                print(current_path)
                parse_source_code(current_path)


def parse_source_code(current_path):
    with open(current_path, 'r') as f:
        file_content = f.read()
        search_constructor_injection(file_content)
        # print(parse_constructor_parameters(file_content))


def search_constructor_injection(source_code):
    c = re.findall(r'constructor\s*?\((.*?)\)|\((.*?)\)', source_code, re.DOTALL)
    for p in c:
        print(p)
    constructor_section = re.search(r'\((.*?)\)', source_code, re.DOTALL)
    if constructor_section:
        parameters = [p.strip() for p in constructor_section.group(1).split(',')]
        print(parameters)
        parameter_types = [p.split(':')[1].strip() for p in parameters if ':' in p]
        for p in parameter_types:
            print(f"\tassertNotNull(get<{p}>())")
    else:
        print('No constructor found')


def parse_constructor_parameters(class_content):
    pattern = r'constructor\s*?\((.*?)\)|\((.*?)\)'
    matches = re.search(pattern, class_content)
    if matches:
        constructor_params = matches.group(1) or matches.group(2)
        if constructor_params:
            return [param.strip() for param in constructor_params.split(',')]
    return []


def search_field_injection(source_code):
    pass


# generate_tests()
parse_source_code('app/src/main/java/com/enginebai/poc/TestInjection.kt')

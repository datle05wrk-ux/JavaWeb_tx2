-- Create databases if not exists
SELECT 'CREATE DATABASE student_db'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'student_db')\gexec

SELECT 'CREATE DATABASE grade_db'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'grade_db')\gexec

SELECT 'CREATE DATABASE analytics_db'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'analytics_db')\gexec

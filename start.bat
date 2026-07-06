@echo off
chcp 65001 > nul
echo ========================================
echo   健身打卡系统 - 一键启动
echo ========================================

echo [1/3] 启动后端服务...
start "Fitness-Server" cmd /c "cd /d %~dp0fitness-server && mvn spring-boot:run"

echo [2/3] 等待后端启动（20秒）...
timeout /t 20 /nobreak > nul

echo [3/3] 启动前端服务...
start "Fitness-Web" cmd /c "cd /d %~dp0fitness-web && npm run dev"

echo ========================================
echo   启动完成！
echo   用户端: http://localhost:5173
echo   管理端: http://localhost:5173/admin/login
echo   默认管理员: admin / admin123
echo   测试用户:   zhangsan / 123456
echo ========================================
pause

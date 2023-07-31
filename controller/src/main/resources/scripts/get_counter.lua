EXPIRE_TIME = 10
local counter = redis.call('INCR', 'counter')

if counter == 1 then
    redis.call('EXPIRE', 'counter', tostring(EXPIRE_TIME))
end

return counter
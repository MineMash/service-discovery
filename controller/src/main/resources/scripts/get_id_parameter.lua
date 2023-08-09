local EXPIRE_TIME = 10

local function safeCounter()
    local counter = redis.call('INCR', 'counter')
    local time = redis.call('TIME')
    local timeMillis = tonumber(time[1]) * 1000 + tonumber(time[2]) / 1000

    if counter == 1 then
        redis.call('EXPIRE', 'counter', tostring(EXPIRE_TIME))
    end

    return tostring(counter) .. ';' .. tostring(timeMillis)
end

return safeCounter()


# 309419.6999,4744328.11

# UTM box: 0.0000, 38.6000, 6.0000, 67.0000
# EPSG:23031 box: 238730.0252, 4276730.7754, 761269.9748, 7434723.1222

class CoordinateConverter
  
  def initialize
    @utm_x = 0.0000
    @utm_y = 38.6000
    @utm_w = 6.0000 - @utm_x
    @utm_h = 67.0000 - @utm_y

    @box_x = 238730.0252
    @box_y = 4276730.7754
    @box_w = 761269.9748 - @box_x
    @box_h = 7434723.1222 - @box_y
  end

  def to_utm_x(x)
    x = @utm_x + ((x.to_f - @box_x) / @box_w) * @utm_w
  end

  def to_utm_y(y)
    y = @utm_y + ((y.to_f - @box_y) / @box_h) * @utm_h
  end
end
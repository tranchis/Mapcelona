class CreateCoordinates < ActiveRecord::Migration
  def self.up
    create_table :coordinates do |t|
      t.float :x
      t.float :y

      t.timestamps
    end
  end

  def self.down
    drop_table :coordinates
  end
end

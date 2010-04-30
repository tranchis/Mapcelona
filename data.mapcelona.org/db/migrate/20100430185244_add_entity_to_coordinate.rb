class AddEntityToCoordinate < ActiveRecord::Migration
  def self.up
    add_column :coordinates, :administrative_entity_id, :integer
  end

  def self.down
    remove_column :coordinates, :administrative_entity_id
  end
end

class CreateAdministrativeEntities < ActiveRecord::Migration
  def self.up
    create_table :administrative_entities do |t|
      t.string :name

      t.timestamps
    end
  end

  def self.down
    drop_table :administrative_entities
  end
end

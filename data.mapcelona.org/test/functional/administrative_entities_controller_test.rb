require 'test_helper'

class AdministrativeEntitiesControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:administrative_entities)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create administrative_entity" do
    assert_difference('AdministrativeEntity.count') do
      post :create, :administrative_entity => { }
    end

    assert_redirected_to administrative_entity_path(assigns(:administrative_entity))
  end

  test "should show administrative_entity" do
    get :show, :id => administrative_entities(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => administrative_entities(:one).to_param
    assert_response :success
  end

  test "should update administrative_entity" do
    put :update, :id => administrative_entities(:one).to_param, :administrative_entity => { }
    assert_redirected_to administrative_entity_path(assigns(:administrative_entity))
  end

  test "should destroy administrative_entity" do
    assert_difference('AdministrativeEntity.count', -1) do
      delete :destroy, :id => administrative_entities(:one).to_param
    end

    assert_redirected_to administrative_entities_path
  end
end

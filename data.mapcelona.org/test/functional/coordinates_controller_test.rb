require 'test_helper'

class CoordinatesControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:coordinates)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create coordinate" do
    assert_difference('Coordinate.count') do
      post :create, :coordinate => { }
    end

    assert_redirected_to coordinate_path(assigns(:coordinate))
  end

  test "should show coordinate" do
    get :show, :id => coordinates(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => coordinates(:one).to_param
    assert_response :success
  end

  test "should update coordinate" do
    put :update, :id => coordinates(:one).to_param, :coordinate => { }
    assert_redirected_to coordinate_path(assigns(:coordinate))
  end

  test "should destroy coordinate" do
    assert_difference('Coordinate.count', -1) do
      delete :destroy, :id => coordinates(:one).to_param
    end

    assert_redirected_to coordinates_path
  end
end
